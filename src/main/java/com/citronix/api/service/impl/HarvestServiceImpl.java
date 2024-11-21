package com.citronix.api.service.impl;

import com.citronix.api.DTO.harvest.HarvestCreateDTO;
import com.citronix.api.domain.Field;
import com.citronix.api.domain.Harvest;
import com.citronix.api.domain.HarvestDetail;
import com.citronix.api.domain.Tree;
import com.citronix.api.domain.enums.SeasonType;
import com.citronix.api.repository.HarvestRepository;
import com.citronix.api.service.FieldService;
import com.citronix.api.service.HarvestDetailService;
import com.citronix.api.service.HarvestService;
import com.citronix.api.service.TreeService;
import com.citronix.api.web.mapper.HarvestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HarvestServiceImpl implements HarvestService {

    private final HarvestDetailService harvestDetailService;
    private final HarvestRepository harvestRepository;
    private final TreeService treeService;
    private final FieldService fieldService;
    private final HarvestMapper harvestMapper;

    @Transactional
    @Override
    public Harvest create(HarvestCreateDTO harvestCreateDTO) {

        Field field = fieldService.findById(harvestCreateDTO.getFieldId());
        SeasonType season = determineSeason(harvestCreateDTO.getHarvestDate());
        int year = harvestCreateDTO.getHarvestDate().getYear();

        if (harvestRepository.existsByFieldAndSeasonAndHarvestDateYear(field, season, year)) {
            throw new IllegalArgumentException("A harvest already exists for this field in the " + season + " season of " + year);
        }

        Harvest harvest = harvestMapper.toHarvest(harvestCreateDTO);
        List<Tree> trees = treeService.productiveTreesByField(field);

        double totalQuantity = calculateTotalProductivity(trees);
        harvest.setTotalQuantity(totalQuantity);
        harvest.setSeason(season);
        harvest = harvestRepository.save(harvest);

        for (Tree tree : trees) {
            HarvestDetail harvestDetail = HarvestDetail.builder()
                    .harvest(harvest)
                    .tree(tree)
                    .quantity(treeService.calculateTreeProductivity(tree))
                    .build();
            harvestDetailService.create(harvestDetail);
        }

        return harvest;
    }


    public double calculateTotalProductivity(List<Tree> trees) {
        return trees.stream()
                .mapToDouble(treeService::calculateTreeProductivity)
                .sum();
    }

    private SeasonType determineSeason(LocalDateTime harvestDate) {

        int month = harvestDate.getMonthValue();

        if (month >= 12 || month <= 2) {
            return SeasonType.WINTER;
        } else if (month <= 5) {
            return SeasonType.SPRING;
        } else if (month <= 8) {
            return SeasonType.SUMMER;
        } else {
            return SeasonType.FALL;
        }


    }

}
