package com.md.email.controller.mapper;

import com.md.email.dto.EmailDto;
import com.md.email.entities.EmailModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmailMapper {

    EmailMapper INSTANCE = Mappers.getMapper(EmailMapper.class);

    EmailModel toEmailEntity(EmailDto dto);
    EmailDto toEmailDto(EmailModel entity);
}
