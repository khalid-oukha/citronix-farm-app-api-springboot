package com.citronix.api.web.mapper;


import com.citronix.api.domain.Farm;
import com.citronix.api.domain.Field;
import com.citronix.api.web.DTO.field.FieldCreateDTO;
import com.citronix.api.web.VM.ResponseFieldVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    Field toField(FieldCreateDTO fieldCreateDTO);

    @Mapping(source = "field.id", target = "id")
    @Mapping(source = "field.name", target = "name")
    @Mapping(source = "field.area", target = "area")
    @Mapping(source = "farm", target = "farm")
    ResponseFieldVM toResponseFieldVM(Field field, Farm farm);
}
