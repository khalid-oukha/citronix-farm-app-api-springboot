package com.citronix.api.service;

import com.citronix.api.DTO.field.FieldCreateDTO;
import com.citronix.api.DTO.field.FieldUpdateDTO;
import com.citronix.api.domain.Field;


public interface FieldService {
    Field create(FieldCreateDTO fieldCreateDTO);

    void delete(Long id);

    Field findById(Long id);

    Field update(Long id, FieldUpdateDTO fieldUpdateDTO);
}
