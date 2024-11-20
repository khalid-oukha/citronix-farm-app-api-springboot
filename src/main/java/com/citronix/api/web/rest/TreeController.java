package com.citronix.api.web.rest;

import com.citronix.api.DTO.tree.TreeCreateDto;
import com.citronix.api.DTO.tree.TreeUpdateDto;
import com.citronix.api.domain.Tree;
import com.citronix.api.service.TreeService;
import com.citronix.api.web.VM.tree.ResponseTreeVM;
import com.citronix.api.web.mapper.TreeMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class TreeController {
    private final TreeService treeService;
    private final TreeMapper treeMapper;


    @GetMapping("/trees/{id}")
    public ResponseEntity<ResponseTreeVM> findById(@PathVariable Long id) {
        Tree tree = treeService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(treeMapper.toResponse(tree));
    }

    @PostMapping("/trees")
    public ResponseEntity<ResponseTreeVM> create(@Valid @RequestBody TreeCreateDto treeCreateDto) {
        Tree tree = treeService.create(treeCreateDto);
        ResponseTreeVM response = treeMapper.toResponse(tree);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/trees/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        treeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/trees/{id}")
    public ResponseEntity<ResponseTreeVM> update(@PathVariable Long id, @Valid @RequestBody TreeUpdateDto treeUpdateDto) {
        Tree tree = treeService.update(id, treeUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body(treeMapper.toResponse(tree));
    }
}
