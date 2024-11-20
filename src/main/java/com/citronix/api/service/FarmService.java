package com.citronix.api.service;

import com.citronix.api.domain.Farm;
import com.citronix.api.web.DTO.farm.FarmCreateDTO;
import com.citronix.api.web.DTO.farm.FarmUpdateDto;

public interface FarmService {
    Farm create(FarmCreateDTO farmRequestDTO);

    void delete(Long id);

    Farm findById(Long id);

    Farm update(Long id, FarmUpdateDto farmRequestDTO);
}
