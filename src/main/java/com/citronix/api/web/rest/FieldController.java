package com.citronix.api.web.rest;

import com.citronix.api.domain.Field;
import com.citronix.api.service.FieldService;
import com.citronix.api.web.DTO.field.FieldCreateDTO;
import com.citronix.api.web.VM.ResponseFieldVM;
import com.citronix.api.web.mapper.FieldMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/vi")
public class FieldController {

    private final FieldService fieldService;
    private final FieldMapper fieldMapper;

    @PostMapping("/fields")
    public ResponseEntity<ResponseFieldVM> create(@Valid @RequestBody FieldCreateDTO fieldCreateDTO) {
        Field field = fieldService.create(fieldCreateDTO);
        ResponseFieldVM response = fieldMapper.toResponseFieldVM(field);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/fields/{id}")
    public ResponseEntity<ResponseFieldVM> findById(@PathVariable Long id) {
        Field field = fieldService.findById(id);
        ResponseFieldVM response = fieldMapper.toResponseFieldVM(field);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/fields/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fieldService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
