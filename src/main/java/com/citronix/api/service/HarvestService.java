package com.citronix.api.service;

import com.citronix.api.DTO.harvest.HarvestCreateDTO;
import com.citronix.api.domain.Harvest;

public interface HarvestService {
    Harvest create(HarvestCreateDTO harvestCreateDTO);
}
