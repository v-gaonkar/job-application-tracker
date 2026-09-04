package com.devmadesimple.jobtracker.dto.gemini.response;

import com.devmadesimple.jobtracker.dto.gemini.request.Content;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Candidate {
    private Content content;
    private String finishReason;
}
