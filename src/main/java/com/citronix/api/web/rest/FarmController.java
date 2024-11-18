package com.citronix.api.web.rest;

import com.citronix.api.domain.Farm;
import com.citronix.api.service.FarmService;
import com.citronix.api.web.DTO.FarmCreateDTO;
import com.citronix.api.web.VM.ResponseFarmVM;
import com.citronix.api.web.mapper.FarmMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vi")
public class FarmController {

    private final FarmService farmService;
    private final FarmMapper farmMapper;

    @Autowired
    public FarmController(FarmService farmService, FarmMapper farmMapper) {
        this.farmService = farmService;
        this.farmMapper = farmMapper;
    }

    @PostMapping("/farms")
    public ResponseEntity<ResponseFarmVM> create(@Valid @RequestBody FarmCreateDTO farmCreateDTO) {
        Farm farm = farmService.create(farmCreateDTO);
        ResponseFarmVM createdFarm = farmMapper.toResponseFarmVM(farm);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFarm);
    }
}
