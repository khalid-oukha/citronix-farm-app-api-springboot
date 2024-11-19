package com.citronix.api.web.mapper;


import com.citronix.api.domain.Field;
import com.citronix.api.web.DTO.field.FieldCreateDTO;
import com.citronix.api.web.VM.ResponseFieldVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    @Mapping(target = "farm.id", source = "farmId")
    Field toField(FieldCreateDTO fieldCreateDTO);

    ResponseFieldVM toResponseFieldVM(Field field);
}
