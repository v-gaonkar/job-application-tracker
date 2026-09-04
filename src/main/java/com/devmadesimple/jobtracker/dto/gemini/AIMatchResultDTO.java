package com.devmadesimple.jobtracker.dto.gemini;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AIMatchResultDTO {
    private List<String> extractedSkills;
    private List<String> interviewTips;
}
