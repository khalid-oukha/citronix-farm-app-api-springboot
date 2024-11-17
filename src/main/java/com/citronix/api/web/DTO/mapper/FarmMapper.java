package com.citronix.api.web.DTO.mapper;


import com.citronix.api.domain.Farm;
import com.citronix.api.web.DTO.FarmCreateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FarmMapper {
    Farm toFarm(FarmCreateDTO farmRequestDTO);
}
