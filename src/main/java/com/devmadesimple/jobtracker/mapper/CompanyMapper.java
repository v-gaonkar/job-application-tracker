package com.devmadesimple.jobtracker.mapper;

import com.devmadesimple.jobtracker.dto.response.CompanyResponse;
import com.devmadesimple.jobtracker.entity.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyResponse toResponse(Company company);
}