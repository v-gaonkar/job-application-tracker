package com.devmadesimple.jobtracker.mapper;

import com.devmadesimple.jobtracker.dto.response.NotificationLogResponse;
import com.devmadesimple.jobtracker.entity.NotificationLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationLogMapper {

    @Mapping(source = "round.id", target = "roundId")
    NotificationLogResponse toResponse(NotificationLog notificationLog);
}