package com.devmadesimple.jobtracker.mapper;

import com.devmadesimple.jobtracker.dto.response.EmailTemplateResponse;
import com.devmadesimple.jobtracker.entity.EmailTemplate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailTemplateMapper {
    EmailTemplateResponse toResponse(EmailTemplate emailTemplate);
}