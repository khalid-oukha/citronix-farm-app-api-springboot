package com.citronix.api.web.mapper;


import com.citronix.api.domain.Farm;
import com.citronix.api.web.DTO.farm.FarmCreateDTO;
import com.citronix.api.web.DTO.farm.FarmUpdateDto;
import com.citronix.api.web.VM.ResponseFarmVM;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FarmMapper {

    @Mapping(source = "createdAt", target = "createdAt")
    Farm toFarm(FarmCreateDTO farmRequestDTO);

    ResponseFarmVM toResponseFarmVM(Farm farm);

    Farm toEntity(FarmUpdateDto farmUpdateDto);

    FarmUpdateDto toDto(Farm farm);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Farm partialUpdate(FarmUpdateDto farmUpdateDto, @MappingTarget Farm farm);
}
