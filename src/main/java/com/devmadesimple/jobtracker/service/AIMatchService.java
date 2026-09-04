package com.devmadesimple.jobtracker.service;

import com.devmadesimple.jobtracker.dto.gemini.AIMatchResultDTO;
import com.devmadesimple.jobtracker.dto.gemini.request.Content;
import com.devmadesimple.jobtracker.dto.gemini.request.GeminiRequest;
import com.devmadesimple.jobtracker.dto.gemini.request.Part;
import com.devmadesimple.jobtracker.dto.gemini.response.GeminiResponse;
import com.devmadesimple.jobtracker.dto.response.AIMatchResponse;
import com.devmadesimple.jobtracker.dto.response.SkillResponse;
import com.devmadesimple.jobtracker.entity.AIMatchResult;
import com.devmadesimple.jobtracker.entity.Application;
import com.devmadesimple.jobtracker.entity.Skill;
import com.devmadesimple.jobtracker.exception.ApplicationNotFoundException;
import com.devmadesimple.jobtracker.repository.AIMatchResultRepository;
import com.devmadesimple.jobtracker.repository.ApplicationRepository;
import com.devmadesimple.jobtracker.repository.SkillRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class AIMatchService {
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    AIMatchResultRepository aiMatchResultRepository;
    @Autowired
    UserSkillService userSkillService;
    @Autowired
    ApplicationSkillService applicationSkillService;
    @Autowired
    SkillRepository skillRepository;
    @Autowired
    CurrentUserProvider currentUserProvider;

    @Value("${gemini.api.url}")
    private String MODEL_URL;

    @Value("${gemini.api.key}")
    private String API_KEY;

    public AIMatchResponse generateMatch(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id " + applicationId));
        if (!application.getUser().getId().equals(currentUserProvider.getCurrentUser().getId())) {
            throw new ApplicationNotFoundException("Application not found with id " + applicationId);
        }

        List<String> userSkillNames = userSkillService.getSkillsForUser().stream()
                .map(SkillResponse::getName)
                .toList();

        AIMatchResultDTO aiResult = analyzeApplication(application.getJobDescription(), userSkillNames);

        // link extracted skills into Application_Skill (creating Skill rows if new)
        for (String skillName : aiResult.getExtractedSkills()) {
            Skill skill = skillRepository.findByName(skillName)
                    .orElseGet(() -> {
                        Skill s = new Skill();
                        s.setName(skillName);
                        return skillRepository.save(s);
                    });
            applicationSkillService.addSkillToApplication(applicationId, skill.getId());
        }

        long overlapCount = aiResult.getExtractedSkills().stream()
                .filter(userSkillNames::contains)
                .count();
        int matchScore = aiResult.getExtractedSkills().isEmpty() ? 0 :
                (int) ((overlapCount * 100) / aiResult.getExtractedSkills().size());

        String tipsJoined = String.join(" ", aiResult.getInterviewTips());

        AIMatchResult result = aiMatchResultRepository.findByApplication_Id(applicationId)
                .orElseGet(AIMatchResult::new);
        result.setApplication(application);
        result.setMatchScore(matchScore);
        result.setInterviewTips(tipsJoined);
        AIMatchResult saved = aiMatchResultRepository.save(result);

        AIMatchResponse response = new AIMatchResponse();
        response.setMatchScore(matchScore);
        response.setExtractedSkills(aiResult.getExtractedSkills());
        response.setInterviewTips(tipsJoined);
        return response;
    }

    public AIMatchResultDTO analyzeApplication(String jobDescriptionText, List<String> userSkills) {

        String prompt = buildPrompt(jobDescriptionText, userSkills);

        Part part = new Part();
        part.setText(prompt);

        Content content = new Content();
        content.setParts(List.of(part));

        GeminiRequest request = new GeminiRequest();
        request.setContents(List.of(content));

        WebClient webClient = WebClient.builder().build();

        GeminiResponse response = webClient.post()
                .uri(MODEL_URL)
                .header("x-goog-api-key", API_KEY)
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GeminiResponse.class)
                .block();

        String rawJson = response.getCandidates().get(0).getContent().getParts().get(0).getText();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(rawJson, AIMatchResultDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse Gemini response as JSON: " + rawJson, e);
        }
    }

    private String buildPrompt(String jobDescriptionText, List<String> userSkills) {
        String skillsList = String.join(", ", userSkills);

        return """
        You are a job-matching assistant for a job application tracker. Given a job description and a candidate's list of current skills, you will:
        1. Extract the specific technical skills, tools, and technologies required or preferred by the job description.
        2. Suggest specific, actionable interview preparation topics based on the job description's emphasis, particularly focusing on any gap between the required skills and the candidate's current skills.

        Respond ONLY with valid JSON in exactly this structure, with no additional text, explanation, or markdown formatting before or after it:

        {
          "extractedSkills": ["skill1", "skill2", "skill3"],
          "interviewTips": ["tip1", "tip2", "tip3"]
        }

        Rules:
        - extractedSkills should contain only concrete technical skills, tools, frameworks, or languages explicitly mentioned or clearly implied by the job description. Do not include soft skills like "communication" or "teamwork".
        - interviewTips should be specific and actionable, referencing particular topics, technologies, or skill areas from the job description — not generic advice like "be confident" or "research the company".
        - If the candidate is missing a skill the job description emphasizes, prioritize a tip addressing that specific gap.
        - Provide 3 to 6 items in each array.

        Job Description:
        %s

        Candidate's Current Skills:
        %s

        Analyze the job description and candidate's skills according to the instructions, and return the JSON response.
        """.formatted(jobDescriptionText, skillsList);
    }
}
