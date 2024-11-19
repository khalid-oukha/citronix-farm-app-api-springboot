package com.citronix.api.web.mapper;


import com.citronix.api.domain.Field;
import com.citronix.api.web.DTO.farm.FarmCreateDTO;
import com.citronix.api.web.VM.ResponseFieldVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    Field toFarm(FarmCreateDTO farmCreateDTO);

    ResponseFieldVM toResponseFieldVM(Field field);
}
