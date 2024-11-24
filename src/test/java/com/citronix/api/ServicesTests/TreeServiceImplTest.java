package com.citronix.api.ServicesTests;

import com.citronix.api.DTO.tree.TreeCreateDto;
import com.citronix.api.DTO.tree.TreeUpdateDto;
import com.citronix.api.domain.Field;
import com.citronix.api.domain.Tree;
import com.citronix.api.repository.TreeRepository;
import com.citronix.api.service.FieldService;
import com.citronix.api.service.impl.TreeServiceImpl;
import com.citronix.api.web.exception.EntityNotFoundException;
import com.citronix.api.web.exception.OutOfSpaceException;
import com.citronix.api.web.mapper.TreeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class TreeServiceImplTest {

    @Mock
    private TreeRepository treeRepository;

    @Mock
    private FieldService fieldService;

    @Mock
    private TreeMapper treeMapper;

    @InjectMocks
    private TreeServiceImpl treeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_should_throw_OutOfSpaceException_if_no_space_for_tree() {
        // Arrange
        TreeCreateDto treeCreateDto = new TreeCreateDto(LocalDateTime.now(), 1L);
        Field field = new Field();
        field.setId(1L);
        field.setArea(500);

        Long totalTreesPerField = 10L;
        double maxTreesPerField = field.getArea() * 10 / 1000;

        when(fieldService.findById(anyLong())).thenReturn(field);
        when(treeRepository.countByField(any(Field.class))).thenReturn(totalTreesPerField);

        // Act & Assert
        OutOfSpaceException exception = assertThrows(OutOfSpaceException.class, () -> {
            treeService.create(treeCreateDto);
        });
        assertEquals("there is no available space for this tree!", exception.getMessage());
    }
    

    @Test
    void delete_should_delete_tree_if_exists() {
        // Arrange
        Tree tree = new Tree();
        tree.setId(1L);
        when(treeRepository.findById(1L)).thenReturn(Optional.of(tree));

        // Act
        treeService.delete(1L);

        // Assert
        verify(treeRepository, times(1)).delete(tree);
    }

    @Test
    void delete_should_throw_EntityNotFoundException_if_tree_not_found() {
        when(treeRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            treeService.delete(1L);
        });
        assertEquals("Tree with id : 1Not found", exception.getMessage());
    }

    @Test
    void update_should_update_tree_details() {
        TreeUpdateDto treeUpdateDto = new TreeUpdateDto(LocalDateTime.now().minusYears(1));
        Tree existingTree = new Tree();
        existingTree.setId(1L);
        existingTree.setPlantationDate(LocalDateTime.now().minusYears(5));
        Tree updatedTree = new Tree();
        updatedTree.setId(1L);
        updatedTree.setPlantationDate(LocalDateTime.now().minusYears(1));

        when(treeRepository.findById(1L)).thenReturn(Optional.of(existingTree));
        when(treeMapper.partialUpdate(any(TreeUpdateDto.class), eq(existingTree))).thenReturn(updatedTree);
        when(treeRepository.save(updatedTree)).thenReturn(updatedTree);

        Tree result = treeService.update(1L, treeUpdateDto);

        assertEquals(updatedTree, result);
        verify(treeRepository, times(1)).save(updatedTree);
    }

    @Test
    void calculateAge_should_return_correct_age() {
        Tree tree = new Tree();
        tree.setPlantationDate(LocalDateTime.now().minusYears(5));

        int age = treeService.calculateAge(tree.getPlantationDate());

        assertEquals(5, age);
    }

    @Test
    void findById_should_return_tree_if_exists() {
        Tree tree = new Tree();
        tree.setId(1L);
        when(treeRepository.findById(1L)).thenReturn(Optional.of(tree));

        Tree foundTree = treeService.findById(1L);

        assertNotNull(foundTree);
        assertEquals(tree, foundTree);
    }

    @Test
    void findById_should_throw_EntityNotFoundException_if_tree_not_found() {
        when(treeRepository.findById(1L)).thenReturn(Optional.empty());

        com.citronix.api.web.exception.EntityNotFoundException exception = assertThrows(com.citronix.api.web.exception.EntityNotFoundException.class, () -> {
            treeService.findById(1L);
        });
        assertEquals("Tree with id : 1Not found", exception.getMessage());
    }

    @Test
    void calculateTreeProductivity_should_return_correct_productivity_based_on_age() {
        Tree tree1 = new Tree();
        tree1.setPlantationDate(LocalDateTime.now().minusYears(2));

        Tree tree2 = new Tree();
        tree2.setPlantationDate(LocalDateTime.now().minusYears(5));

        Tree tree3 = new Tree();
        tree3.setPlantationDate(LocalDateTime.now().minusYears(15));

        assertEquals(2.5, treeService.calculateTreeProductivity(tree1));
        assertEquals(12, treeService.calculateTreeProductivity(tree2));
        assertEquals(20, treeService.calculateTreeProductivity(tree3));
    }

}
