package com.citronix.api.web.mapper;

import com.citronix.api.DTO.harvest.HarvestCreateDTO;
import com.citronix.api.domain.Harvest;
import com.citronix.api.web.VM.Harvest.HarvestResponseVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HarvestMapper {
    Harvest toHarvest(HarvestCreateDTO harvestCreateDTO);

    HarvestResponseVM toResponse(Harvest harvest);
}
