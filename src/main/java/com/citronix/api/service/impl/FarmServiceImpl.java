package com.citronix.api.service.impl;

import com.citronix.api.domain.Farm;
import com.citronix.api.repository.FarmRepository;
import com.citronix.api.service.FarmService;
import com.citronix.api.web.DTO.FarmCreateDTO;
import com.citronix.api.web.exception.EntityAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;

    public FarmServiceImpl(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    @Override
    public Farm create(FarmCreateDTO farmRequestDTO) {
        if (farmRepository.existsByName(farmRequestDTO.getName())) {
            throw new EntityAlreadyExistsException("Farm with the name '" + farmRequestDTO.getName() + "' already exists.");
        }

        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Farm findById(Long id) {
        return null;
    }

    @Override
    public Farm update(FarmCreateDTO farmRequestDTO) {
        return null;
    }
}
