package com.citronix.api.service;

import com.citronix.api.domain.Farm;
import com.citronix.api.web.DTO.FarmCreateDTO;

public interface FarmService {
    Farm create(FarmCreateDTO farmRequestDTO);

    void delete(Long id);

    Farm findById(Long id);

    Farm update(FarmCreateDTO farmRequestDTO);
}
