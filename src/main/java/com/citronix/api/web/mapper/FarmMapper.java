package com.citronix.api.web.mapper;


import com.citronix.api.DTO.farm.FarmCreateDTO;
import com.citronix.api.DTO.farm.FarmUpdateDto;
import com.citronix.api.domain.Farm;
import com.citronix.api.web.VM.Farm.ResponseFarmVM;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FarmMapper {

    @Mapping(source = "createdAt", target = "createdAt")
    Farm toFarm(FarmCreateDTO farmRequestDTO);

    ResponseFarmVM toResponseFarmVM(Farm farm);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Farm partialUpdate(FarmUpdateDto farmUpdateDto, @MappingTarget Farm farm);

}
