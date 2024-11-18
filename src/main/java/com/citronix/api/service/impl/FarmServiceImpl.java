package com.citronix.api.service.impl;

import com.citronix.api.domain.Farm;
import com.citronix.api.repository.FarmRepository;
import com.citronix.api.service.FarmService;
import com.citronix.api.web.DTO.FarmCreateDTO;
import com.citronix.api.web.exception.EntityAlreadyExistsException;
import com.citronix.api.web.mapper.FarmMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;

    public FarmServiceImpl(FarmRepository farmRepository,
                           FarmMapper farmMapper) {
        this.farmRepository = farmRepository;
        this.farmMapper = farmMapper;
    }

    @Override
    public Farm create(FarmCreateDTO farmRequestDTO) {
        if (farmRepository.existsByName(farmRequestDTO.getName())) {
            throw new EntityAlreadyExistsException("Farm with the name '" + farmRequestDTO.getName() + "' already exists.");
        }

        if (farmRequestDTO.getCreatedAt() == null) {
            farmRequestDTO.setCreatedAt(LocalDateTime.now());
        }

        return farmRepository.save(farmMapper.toFarm(farmRequestDTO));
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
