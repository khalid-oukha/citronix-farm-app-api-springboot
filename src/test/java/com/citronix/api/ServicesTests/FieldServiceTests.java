package com.citronix.api.ServicesTests;

import com.citronix.api.DTO.field.FieldCreateDTO;
import com.citronix.api.DTO.field.FieldUpdateDTO;
import com.citronix.api.Helpers.FieldValidator;
import com.citronix.api.domain.Farm;
import com.citronix.api.domain.Field;
import com.citronix.api.repository.FieldRepository;
import com.citronix.api.service.FarmService;
import com.citronix.api.service.impl.FieldServiceImpl;
import com.citronix.api.web.exception.EntityNotFoundException;
import com.citronix.api.web.mapper.FieldMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FieldServiceTests {


    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FieldMapper fieldMapper;

    @Mock
    private FarmService farmService;

    @Mock
    private FieldValidator fieldValidator;

    @InjectMocks
    private FieldServiceImpl fieldService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_should_create_new_field_when_name_does_not_exist() {
        Long farmId = 1000L;

        Farm farm = Farm.builder()
                .id(farmId)
                .name("farm 1 test")
                .area(2000.0)
                .location("Location test")
                .build();

        FieldCreateDTO fieldCreateDTO = FieldCreateDTO.builder()
                .name("Field Alpha")
                .area(15.5)
                .farmId(farmId)
                .build();

        Field newField = Field.builder()
                .name(fieldCreateDTO.getName())
                .area(fieldCreateDTO.getArea())
                .build();

        when(farmService.findById(farmId)).thenReturn(farm);

        when(fieldMapper.toField(fieldCreateDTO)).thenReturn(newField);

        when(fieldRepository.save(any(Field.class))).thenAnswer(invocation -> {
            Field savedField = invocation.getArgument(0);
            savedField.setId(1L);
            return savedField;
        });

        Field result = fieldService.create(fieldCreateDTO);

        assertEquals("Field Alpha", result.getName());
        assertEquals(15.5, result.getArea());
        assertEquals(farm, result.getFarm());
    }

    @Test
    void create_should_throw_exception_when_farm_has_too_many_fields() {
        Long farmId = 1000L;
        FieldCreateDTO fieldCreateDTO = FieldCreateDTO.builder()
                .name("Field Alpha")
                .area(2000.0)
                .farmId(farmId)
                .build();

        Farm farm = Farm.builder()
                .id(farmId)
                .name("Farm Omega")
                .area(10000.0)
                .fields(
                        List.of()
                )
                .build();

        when(farmService.findById(farmId)).thenReturn(farm);
        when(fieldRepository.countByFarm(farm)).thenReturn(11L);

        Field newField = Field.builder()
                .name("Field Alpha")
                .area(2000.0)
                .farm(farm)
                .build();
        when(fieldMapper.toField(fieldCreateDTO)).thenReturn(newField);

    }


    @Test
    void delete_should_delete_field_when_it_exists() {
        Long fieldId = 1L;
        Field existingField = Field.builder()
                .id(fieldId)
                .name("Field Alpha")
                .build();

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(existingField));

        fieldService.delete(fieldId);
        verify(fieldRepository).delete(existingField);

    }

    @Test
    void delete_should_throw_exception_when_field_does_not_exist() {
        Long fieldId = 1L;

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> fieldService.delete(fieldId));

        assertEquals("Field with id : 1 not found", exception.getMessage());
        verify(fieldRepository, never()).delete(any(Field.class));

    }

    @Test
    void find_by_id_should_return_field_when_it_exists() {
        Long fieldId = 1L;
        Field expectedField = Field.builder()
                .id(fieldId)
                .name("Field Alpha")
                .build();

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(expectedField));

        Field result = fieldService.findById(fieldId);

        assertEquals(expectedField, result);
    }

    @Test
    void find_by_id_should_throw_exception_when_field_does_not_exist() {
        Long fieldId = 1L;

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> fieldService.findById(fieldId));

        assertEquals("Field with id : 1 not found", exception.getMessage());
    }

    @Test
    void update_should_update_field_successfully_when_it_exists() {
        Long fieldId = 1L;

        Farm farm = Farm.builder()
                .id(100L)
                .name("Farm Omega")
                .area(5000.0)
                .build();


        Field existingField = Field.builder()
                .id(fieldId)
                .name("Old Field Name")
                .farm(farm)
                .area(1000.0)
                .build();

        Field updatedField = Field.builder()
                .id(fieldId)
                .name("New Field Name")
                .farm(farm)
                .area(1500.0)
                .build();

        FieldUpdateDTO updateDTO = FieldUpdateDTO.builder()
                .name("New Field Name")
                .area(1500.0)
                .build();

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(existingField));
        when(fieldMapper.partialUpdate(updateDTO, existingField)).thenReturn(updatedField);
        when(fieldRepository.save(updatedField)).thenReturn(updatedField);

        Field result = fieldService.update(fieldId, updateDTO);

        assertEquals(updatedField, result);
        assertEquals("New Field Name", result.getName());
        assertNotNull(result.getFarm());
        assertEquals(farm.getArea(), result.getFarm().getArea());
        assertEquals(1500.0, result.getArea());
    }


    @Test
    void update_should_throw_exception_when_field_does_not_exist() {
        Long fieldId = 1L;

        FieldUpdateDTO updateDTO = FieldUpdateDTO.builder()
                .name("New Field Name")
                .build();

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> fieldService.update(fieldId, updateDTO));

        assertEquals("Field with id : 1 not found", exception.getMessage());
    }
}
