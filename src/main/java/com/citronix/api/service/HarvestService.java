package com.citronix.api.service;

import com.citronix.api.DTO.harvest.HarvestCreateDTO;
import com.citronix.api.domain.Harvest;
import com.citronix.api.domain.Tree;

import java.util.List;

public interface HarvestService {
    Harvest create(HarvestCreateDTO harvestCreateDTO);

    Harvest findById(Long id);

    double calculateTotalProductivity(List<Tree> trees);

    void delete(Long id);
}
