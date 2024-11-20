package com.citronix.api.service;


import com.citronix.api.DTO.tree.TreeCreateDto;
import com.citronix.api.domain.Tree;

public interface TreeService {
    Tree create(TreeCreateDto treeCreateDto);

    void delete(Long id);

    Tree findById(Long id);

}
