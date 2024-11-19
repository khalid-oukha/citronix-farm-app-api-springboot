package com.citronix.api.service;

import com.citronix.api.domain.Field;
import com.citronix.api.web.DTO.field.FieldCreateDTO;
import com.citronix.api.web.DTO.field.FieldUpdateDTO;

public interface FieldService {
    Field create(FieldCreateDTO fieldCreateDTO);

    void delete(Long id);

    Field findById(Long id);

    Field update(Long id, FieldUpdateDTO fieldUpdateDTO);
}
