package com.devmadesimple.jobtracker.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailTemplateResponse {
    private Long id;
    private String name;
    private String subject;
    private String body;
    private String category;
}