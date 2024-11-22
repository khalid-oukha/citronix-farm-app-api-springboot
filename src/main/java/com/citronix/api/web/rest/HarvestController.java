package com.citronix.api.web.rest;

import com.citronix.api.DTO.harvest.HarvestCreateDTO;
import com.citronix.api.domain.Harvest;
import com.citronix.api.service.HarvestService;
import com.citronix.api.web.VM.Harvest.HarvestResponseVM;
import com.citronix.api.web.mapper.HarvestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class HarvestController {

    private final HarvestService harvestService;
    private final HarvestMapper harvestMapper;

    @PostMapping("/harvest")
    public ResponseEntity<HarvestResponseVM> create(@Valid @RequestBody HarvestCreateDTO harvestCreateDTO) {
        Harvest harvest = harvestService.create(harvestCreateDTO);
        HarvestResponseVM response = harvestMapper.toResponse(harvest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/harvest/{id}")
    public ResponseEntity<HarvestResponseVM> findById(@PathVariable Long id) {
        Harvest harvest = harvestService.findById(id);
        HarvestResponseVM response = harvestMapper.toResponse(harvest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
