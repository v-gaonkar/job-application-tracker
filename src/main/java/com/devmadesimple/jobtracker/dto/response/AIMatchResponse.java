package com.devmadesimple.jobtracker.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class AIMatchResponse {
    private Integer matchScore;
    private List<String> extractedSkills;
    private String interviewTips;
}