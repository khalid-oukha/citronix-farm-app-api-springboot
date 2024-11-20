package com.citronix.api.service.impl;

import com.citronix.api.DTO.tree.TreeCreateDto;
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

    }

    @Override
    public Tree findById(Long id) {
        return treeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tree with id : " + id + "Not found"));
    }
}
