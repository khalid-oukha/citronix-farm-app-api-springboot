package com.citronix.api.web.rest;

import com.citronix.api.DTO.farm.FarmCreateDTO;
import com.citronix.api.DTO.farm.FarmUpdateDto;
import com.citronix.api.domain.Farm;
import com.citronix.api.service.FarmService;
import com.citronix.api.web.VM.Farm.ResponseFarmVM;
import com.citronix.api.web.mapper.FarmMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
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

    @GetMapping("/farms/{id}")
    public ResponseEntity<ResponseFarmVM> findById(@PathVariable Long id) {
        Farm farm = farmService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(farmMapper.toResponseFarmVM(farm));
    }

    @DeleteMapping("/farms/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        farmService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Farm with ID " + id + " deleted successfully.");
    }

    @PutMapping("/farms/{id}")
    public ResponseEntity<ResponseFarmVM> update(@PathVariable Long id, @Valid @RequestBody FarmUpdateDto farmUpdateDto) {
        Farm farm = farmService.update(id, farmUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body(farmMapper.toResponseFarmVM(farm));
    }


    @GetMapping("/farms/search")
    public ResponseEntity<Page<ResponseFarmVM>> searchFarms(@RequestParam(required = false) String name,
                                                            @RequestParam(required = false) String location,
                                                            @RequestParam(required = false) Double minArea,
                                                            @RequestParam(required = false) Double maxArea,
                                                            @RequestParam(required = false) LocalDateTime startDate,
                                                            @RequestParam(required = false) LocalDateTime endDate,
                                                            Pageable pageable
    ) {
        Page<ResponseFarmVM> response = farmService.searchFarms(name, location, minArea, maxArea, startDate, endDate, pageable).map(farmMapper::toResponseFarmVM);
        return ResponseEntity.ok(response);
    }

}
