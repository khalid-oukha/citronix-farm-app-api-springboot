package com.citronix.api.web.mapper;

import com.citronix.api.DTO.harvest.HarvestCreateDTO;
import com.citronix.api.domain.Harvest;
import com.citronix.api.domain.HarvestDetail;
import com.citronix.api.web.VM.Harvest.HarvestResponseVM;
import com.citronix.api.web.VM.ResponseHarvestDetailVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HarvestMapper {
    Harvest toHarvest(HarvestCreateDTO harvestCreateDTO);

    @Mapping(target = "harvestDetails", source = "harvestDetails")
    HarvestResponseVM toResponse(Harvest harvest);

    @Mapping(source = "tree.id", target = "treeId")
    @Mapping(source = "tree.plantationDate", target = "treePlantationDate")
    ResponseHarvestDetailVM toResponse(HarvestDetail harvestDetail);

    List<ResponseHarvestDetailVM> toResponse(List<HarvestDetail> harvestDetails);
}
