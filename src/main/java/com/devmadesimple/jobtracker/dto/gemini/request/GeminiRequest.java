package com.devmadesimple.jobtracker.dto.gemini.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeminiRequest {
    private List<Content> contents;
}

