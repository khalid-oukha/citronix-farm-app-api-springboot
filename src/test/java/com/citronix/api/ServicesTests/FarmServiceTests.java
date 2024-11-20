package com.citronix.api.ServicesTests;

import com.citronix.api.domain.Farm;
import com.citronix.api.repository.FarmRepository;
import com.citronix.api.service.impl.FarmServiceImpl;
import com.citronix.api.web.DTO.farm.FarmCreateDTO;
import com.citronix.api.web.DTO.farm.FarmUpdateDto;
import com.citronix.api.web.exception.EntityAlreadyExistsException;
import com.citronix.api.web.exception.EntityNotFoundException;
import com.citronix.api.web.mapper.FarmMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class FarmServiceTests {

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private FarmMapper farmMapper;

    @InjectMocks
    private FarmServiceImpl farmService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_create_farm_successfully() {
        FarmCreateDTO farmCreateDTO = FarmCreateDTO.builder()
                .name("farm khalid oukha")
                .location("youssofia")
                .area(40.33)
                .build();

        Farm beforeFarm = farmMapper.toFarm(farmCreateDTO);

        when(farmRepository.existsByName(farmCreateDTO.getName())).thenReturn(false);
        when(farmRepository.save(ArgumentMatchers.any(Farm.class))).thenReturn(beforeFarm);

        Farm result = farmService.create(farmCreateDTO);

        assertEquals(beforeFarm, result, "The result should match the saved user");
    }

    @Test
    void should_throw_entity_already_exists_exception_when_farm_name_exists() {
        FarmCreateDTO farmCreateDTO = FarmCreateDTO.builder()
                .name("farm khalid oukha")
                .location("youssofia")
                .area(40.33)
                .build();

        when(farmRepository.existsByName(farmCreateDTO.getName())).thenReturn(true);

        EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class, () -> {
            farmService.create(farmCreateDTO);
        });

        assertEquals("Farm with the name 'farm khalid oukha' already exists.", exception.getMessage());
    }

    @Test
    void should_delete_farm_successfully() {
        Long idFarm = 1L;
        Farm farm = Farm.builder().id(idFarm).name("farm").location("youssofia").area(40.05).build();
        when(farmRepository.findById(1L)).thenReturn(Optional.ofNullable(farm));

        farmService.delete(1L);

        verify(farmRepository).delete(farm);
    }

    @Test
    void should_throw_entity_not_found_exception_when_deleting_non_existing_farm() {
        Long idFarm = 1L;
        when(farmRepository.findById(idFarm)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> farmService.delete(idFarm));

        assertEquals("Farm with id '1' not found", exception.getMessage());

        verify(farmRepository, never()).delete(any(Farm.class));
    }

    @Test
    void should_find_farm_by_id_successfully() {
        Long farmId = 1L;
        Farm expectedFarm = Farm.builder()
                .id(farmId)
                .name("farm khalid oukha")
                .location("youssofia")
                .area(40.33)
                .build();

        when(farmRepository.findById(farmId)).thenReturn(Optional.ofNullable(expectedFarm));

        Farm result = farmService.findById(farmId);
        assertEquals(expectedFarm, result);
    }

    @Test
    void should_throw_entity_not_found_exception_when_farm_not_found_by_id() {
        Long farmId = 1L;

        when(farmRepository.findById(farmId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> farmService.findById(farmId));

        assertEquals("Farm with id '1' not found", exception.getMessage());
    }

    @Test
    void should_update_farm_successfully() {
        // Arrange
        Long farmId = 1L;

        Farm existingFarm = Farm.builder()
                .id(farmId)
                .name("old farm name")
                .location("old location")
                .area(20.0)
                .build();

        Farm updatedFarm = Farm.builder()
                .id(farmId)
                .name("new farm name")
                .location("new location")
                .area(50.0)
                .build();

        FarmUpdateDto updateDTO = FarmUpdateDto.builder()
                .name("new farm name")
                .location("new location")
                .area(50.0)
                .build();

        when(farmRepository.findById(farmId)).thenReturn(Optional.of(existingFarm));
        when(farmMapper.partialUpdate(updateDTO, existingFarm)).thenReturn(updatedFarm);
        when(farmRepository.save(updatedFarm)).thenReturn(updatedFarm);

        Farm result = farmService.update(farmId, updateDTO);

        assertEquals(updatedFarm, result);
        assertEquals("new farm name", result.getName());
        assertEquals("new location", result.getLocation());
        assertEquals(50.0, result.getArea());
    }


    @Test
    void should_throw_entity_not_found_exception_when_updating_non_existing_farm() {
        Long farmId = 1L;
        FarmUpdateDto updateDTO = FarmUpdateDto.builder()
                .name("new farm name")
                .location("new location")
                .area(50.0)
                .build();

        when(farmRepository.findById(farmId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> farmService.update(farmId, updateDTO));

        assertEquals("Farm with id '1' not found", exception.getMessage());
    }


}
