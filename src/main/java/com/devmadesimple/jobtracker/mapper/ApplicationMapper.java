package com.devmadesimple.jobtracker.mapper;

import com.devmadesimple.jobtracker.dto.response.ApplicationResponse;
import com.devmadesimple.jobtracker.entity.Application;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {
    ApplicationResponse toResponse(Application application);
}
