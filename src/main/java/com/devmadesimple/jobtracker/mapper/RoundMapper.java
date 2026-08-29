package com.devmadesimple.jobtracker.mapper;

import com.devmadesimple.jobtracker.dto.response.RoundResponse;
import com.devmadesimple.jobtracker.entity.Round;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoundMapper {

    @Mapping(source = "application.id", target = "applicationId")
    @Mapping(source = "application.jobTitle", target = "applicationJobTitle")
    @Mapping(source = "application.company.name", target = "companyName")
    RoundResponse toResponse(Round round);
}