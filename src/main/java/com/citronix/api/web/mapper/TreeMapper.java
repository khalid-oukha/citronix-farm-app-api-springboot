package com.citronix.api.web.mapper;

import com.citronix.api.DTO.tree.TreeCreateDto;
import com.citronix.api.domain.Tree;
import com.citronix.api.web.VM.tree.ResponseTreeVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TreeMapper {

    Tree toTree(TreeCreateDto treeCreateDto);

    ResponseTreeVM toResponse(Tree tree);
}
