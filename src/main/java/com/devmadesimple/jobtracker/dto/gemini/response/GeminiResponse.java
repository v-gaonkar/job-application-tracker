package com.devmadesimple.jobtracker.dto.gemini.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeminiResponse {
    private List<Candidate> candidates;
}


