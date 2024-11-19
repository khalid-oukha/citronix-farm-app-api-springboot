package com.citronix.api.service.impl;

import com.citronix.api.Helpers.FieldValidator;
import com.citronix.api.domain.Farm;
import com.citronix.api.domain.Field;
import com.citronix.api.repository.FieldRepository;
import com.citronix.api.service.FarmService;
import com.citronix.api.service.FieldService;
import com.citronix.api.web.DTO.field.FieldCreateDTO;
import com.citronix.api.web.DTO.field.FieldUpdateDTO;
import com.citronix.api.web.mapper.FieldMapper;

public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FieldMapper fieldMapper;
    private final FarmService farmService;
    private final FieldValidator fieldValidator;

    public FieldServiceImpl(FieldRepository fieldRepository,
                            FieldMapper fieldMapper,
                            FarmService farmService,
                            FieldValidator fieldValidator) {
        this.fieldRepository = fieldRepository;
        this.fieldMapper = fieldMapper;
        this.farmService = farmService;
        this.fieldValidator = fieldValidator;
    }

    @Override
    public Field create(FieldCreateDTO fieldCreateDTO) {
        Farm farm = farmService.findById(fieldCreateDTO.getFarmId());
        
        fieldValidator.validateFieldCount(fieldRepository.countByFarm(farm));
        fieldValidator.validateFieldArea(farm.getArea(), fieldCreateDTO.getArea());
        fieldValidator.validateTotalFieldArea(farm.getArea(), fieldRepository.sumAreaByFarm(farm), fieldCreateDTO.getArea());

        return fieldRepository.save(fieldMapper.toField(fieldCreateDTO));
    }


    @Override
    public void delete(Long id) {

    }

    @Override
    public Field findById(Long id) {
        return null;
    }

    @Override
    public Field update(Long id, FieldUpdateDTO fieldUpdateDTO) {
        return null;
    }
}
