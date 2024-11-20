package com.citronix.api.service;

import com.citronix.api.DTO.farm.FarmCreateDTO;
import com.citronix.api.DTO.farm.FarmUpdateDto;
import com.citronix.api.domain.Farm;

public interface FarmService {
    Farm create(FarmCreateDTO farmRequestDTO);

    void delete(Long id);

    Farm findById(Long id);

    Farm update(Long id, FarmUpdateDto farmRequestDTO);
}
