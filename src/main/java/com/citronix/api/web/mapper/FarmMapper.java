package com.citronix.api.web.mapper;


import com.citronix.api.domain.Farm;
import com.citronix.api.web.DTO.FarmCreateDTO;
import com.citronix.api.web.VM.ResponseFarmVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FarmMapper {
    
    @Mapping(source = "createdAt", target = "createdAt")
    Farm toFarm(FarmCreateDTO farmRequestDTO);

    ResponseFarmVM toResponseFarmVM(Farm farm);
}
