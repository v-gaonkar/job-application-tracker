package com.devmadesimple.jobtracker.mapper;

import com.devmadesimple.jobtracker.dto.response.ContactResponse;
import com.devmadesimple.jobtracker.entity.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactResponse toResponse(Contact contact);
}