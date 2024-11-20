package com.citronix.api.service.impl;

import com.citronix.api.DTO.field.FieldCreateDTO;
import com.citronix.api.DTO.field.FieldUpdateDTO;
import com.citronix.api.Helpers.FieldValidator;
import com.citronix.api.domain.Farm;
import com.citronix.api.domain.Field;
import com.citronix.api.repository.FieldRepository;
import com.citronix.api.service.FarmService;
import com.citronix.api.service.FieldService;
import com.citronix.api.web.exception.EntityNotFoundException;
import com.citronix.api.web.mapper.FieldMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

        List<Field> fields = fieldRepository.findByFarm(farm);

        double totalFieldsArea = fields.stream()
                .mapToDouble(Field::getArea)
                .sum();

        fieldValidator.validateTotalFieldArea(farm.getArea(), totalFieldsArea, fieldCreateDTO.getArea());
        Field field = fieldMapper.toField(fieldCreateDTO);
        field.setFarm(farm);
        return fieldRepository.save(field);
    }

    @Override
    public Field findById(Long id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Field with id : " + id + " not found"));
    }

    @Override
    public void delete(Long id) {
        Field field = findById(id);
        fieldRepository.delete(field);
    }

    @Override
    public Field update(Long id, FieldUpdateDTO fieldUpdateDTO) {
        Field field = findById(id);
        double totalFarmArea = field.getFarm().getArea() - fieldUpdateDTO.getArea();
        fieldValidator.validateFieldArea(totalFarmArea, fieldUpdateDTO.getArea());

        List<Field> fields = fieldRepository.findByFarm(field.getFarm());
        double totalFieldsArea = fields.stream()
                .filter(f -> !f.getId().equals(id))
                .mapToDouble(Field::getArea)
                .sum();

        fieldValidator.validateTotalFieldArea(field.getFarm().getArea(), totalFieldsArea, fieldUpdateDTO.getArea());

        Field updatedField = fieldMapper.partialUpdate(fieldUpdateDTO, field);
        return fieldRepository.save(updatedField);
    }
}
