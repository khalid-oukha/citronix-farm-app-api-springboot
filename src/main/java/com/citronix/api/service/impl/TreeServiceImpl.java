package com.citronix.api.service.impl;

import com.citronix.api.DTO.tree.TreeCreateDto;
import com.citronix.api.DTO.tree.TreeUpdateDto;
import com.citronix.api.domain.Field;
import com.citronix.api.domain.Tree;
import com.citronix.api.domain.enums.TreeStatus;
import com.citronix.api.repository.TreeRepository;
import com.citronix.api.service.FieldService;
import com.citronix.api.service.TreeService;
import com.citronix.api.web.exception.EntityNotFoundException;
import com.citronix.api.web.exception.OutOfSpaceException;
import com.citronix.api.web.mapper.TreeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@RequiredArgsConstructor
@Service
public class TreeServiceImpl implements TreeService {
    private final TreeRepository treeRepository;
    private final TreeMapper treeMapper;
    private final FieldService fieldService;

    @Override
    public Tree create(TreeCreateDto treeCreateDto) {
        Field field = fieldService.findById(treeCreateDto.getFieldId());
        Long totalTreesPerField = treeRepository.countByField(field);
        double maxTreesPerField = field.getArea() * 10 / 1000;

        if (totalTreesPerField + 1 > maxTreesPerField) {
            throw new OutOfSpaceException("there is no available space for this tree!");
        }

        Tree tree = treeMapper.toTree(treeCreateDto);
        int age = calculateAge(tree.getPlantationDate());

        tree.setStatus(determineStatusByAge(age));
        tree.setField(field);

        return treeRepository.save(tree);
    }

    @Override
    public void delete(Long id) {
        Tree tree = findById(id);
        treeRepository.delete(tree);
    }

    @Override
    public Tree update(Long id, TreeUpdateDto treeUpdateDto) {
        Tree existingTree = findById(id);
        Tree updateTree = treeMapper.partialUpdate(treeUpdateDto, existingTree);
        int age = calculateAge(updateTree.getPlantationDate());

        updateTree.setStatus(determineStatusByAge(age));
        updateTree.setField(existingTree.getField());

        return treeRepository.save(updateTree);
    }

    public int calculateAge(LocalDateTime plantationDate) {
        Period period = Period.between(plantationDate.toLocalDate(), LocalDate.now());
        return period.getYears();
    }

    @Override
    public Tree findById(Long id) {
        return treeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tree with id : " + id + "Not found"));
    }

    private TreeStatus determineStatusByAge(int age) {
        if (age < 3) {
            return TreeStatus.YOUNG;
        } else if (age <= 10) {
            return TreeStatus.MATURE;
        } else if (age < 20) {
            return TreeStatus.OLD;
        } else {
            return TreeStatus.NON_PRODUCTIVE;
        }
    }

}
