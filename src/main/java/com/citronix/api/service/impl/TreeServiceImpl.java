package com.citronix.api.service.impl;

import com.citronix.api.DTO.tree.TreeCreateDto;
import com.citronix.api.DTO.tree.TreeUpdateDto;
import com.citronix.api.domain.Field;
import com.citronix.api.domain.Tree;
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
import java.util.List;

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

    @Override
    public List<Tree> productiveTreesByField(Field field) {
        LocalDateTime twentyYearsAgo = LocalDateTime.now().minusYears(20);
        return treeRepository.findByFieldAndPlantationDateAfter(field, twentyYearsAgo);
    }

    @Override
    public double calculateTreeProductivity(Tree tree) {
        int age = calculateAge(tree.getPlantationDate());
        double productivity = 0;
        if (age < 3) {
            return productivity = 2.5;
        } else if (age > 3 && age < 10) {
            return productivity = 12;
        } else if (age > 10 && age < 20) {
            return productivity = 20;
        }
        return productivity;
    }
}
