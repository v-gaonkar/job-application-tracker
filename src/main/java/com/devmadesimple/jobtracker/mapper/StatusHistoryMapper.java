package com.devmadesimple.jobtracker.mapper;

import com.devmadesimple.jobtracker.dto.response.StatusHistoryResponse;
import com.devmadesimple.jobtracker.entity.StatusHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusHistoryMapper {
    StatusHistoryResponse toResponse(StatusHistory statusHistory);
}