package com.citronix.api.service.impl;

import com.citronix.api.DTO.harvest.HarvestCreateDTO;
import com.citronix.api.DTO.harvest.HarvestUpdateDTO;
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
import com.citronix.api.web.exception.EntityNotFoundException;
import com.citronix.api.web.mapper.HarvestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HarvestServiceImpl implements HarvestService {

    private final HarvestDetailService harvestDetailService;
    private final HarvestRepository harvestRepository;
    private final TreeService treeService;
    private final FieldService fieldService;
    private final HarvestMapper harvestMapper;


    @Override
    public Harvest findById(Long id) {
        return harvestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("harvest not found"));
    }


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

        List<HarvestDetail> harvestDetails = addAllHarvestDetails(harvest, trees);


        harvest.setHarvestDetails(harvestDetails);
        return harvest;
    }

    @Override
    public double calculateTotalProductivity(List<Tree> trees) {
        return trees.stream()
                .mapToDouble(treeService::calculateTreeProductivity)
                .sum();
    }

    @Override
    public void delete(Long id) {
        Harvest harvest = findById(id);
        harvestRepository.delete(harvest);
    }

    @Transactional
    @Override
    public Harvest update(Long id, HarvestUpdateDTO harvestUpdateDTO) {
        Harvest existingHarvest = findById(id);

        LocalDateTime newHarvestDate = harvestUpdateDTO.getHarvestDate();

        SeasonType newSeason = determineSeason(newHarvestDate);

        Field field = existingHarvest.getHarvestDetails().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Harvest has no associated trees"))
                .getTree()
                .getField();

        int newYear = newHarvestDate.getYear();
        if (harvestRepository.existsByFieldAndSeasonAndHarvestDateYear(field, newSeason, newYear)) {
            throw new IllegalArgumentException(
                    "A harvest already exists for this field in the " + newSeason + " season of " + newYear);
        }

        existingHarvest.setHarvestDate(newHarvestDate);
        existingHarvest.setSeason(newSeason);

        return harvestRepository.save(existingHarvest);
    }


    public List<HarvestDetail> addAllHarvestDetails(Harvest harvest, List<Tree> trees) {
        List<HarvestDetail> harvestDetails = new ArrayList<>();
        for (Tree tree : trees) {
            HarvestDetail harvestDetail = HarvestDetail.builder()
                    .harvest(harvest)
                    .tree(tree)
                    .quantity(treeService.calculateTreeProductivity(tree))
                    .build();
            harvestDetails.add(harvestDetail);
        }
        harvestDetailService.createAll(harvestDetails);
        return harvestDetails;
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
