package com.devmadesimple.jobtracker.mapper;

import com.devmadesimple.jobtracker.dto.response.ApplicationResponse;
import com.devmadesimple.jobtracker.entity.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "company.website", target = "companyWebsite")
    @Mapping(source = "company.careerPageUrl", target = "companyCareerPageUrl")
    ApplicationResponse toResponse(Application application);
}