package com.citronix.api.web.mapper;

import com.citronix.api.DTO.tree.TreeCreateDto;
import com.citronix.api.DTO.tree.TreeUpdateDto;
import com.citronix.api.domain.Tree;
import com.citronix.api.web.VM.tree.ResponseTreeVM;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TreeMapper {

    Tree toTree(TreeCreateDto treeCreateDto);

    ResponseTreeVM toResponse(Tree tree);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tree partialUpdate(TreeUpdateDto treeUpdateDto, @MappingTarget Tree tree);
}
