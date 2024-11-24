package com.citronix.api.service.impl;

import com.citronix.api.DTO.farm.FarmCreateDTO;
import com.citronix.api.DTO.farm.FarmUpdateDto;
import com.citronix.api.domain.Farm;
import com.citronix.api.repository.FarmRepository;
import com.citronix.api.service.FarmService;
import com.citronix.api.web.exception.EntityAlreadyExistsException;
import com.citronix.api.web.exception.EntityNotFoundException;
import com.citronix.api.web.mapper.FarmMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public FarmServiceImpl(FarmRepository farmRepository,
                           FarmMapper farmMapper) {
        this.farmRepository = farmRepository;
        this.farmMapper = farmMapper;
    }

    @Override
    public Farm create(FarmCreateDTO farmRequestDTO) {
        if (farmRepository.existsByName(farmRequestDTO.getName())) {
            throw new EntityAlreadyExistsException("Farm with the name '" + farmRequestDTO.getName() + "' already exists.");
        }
        return farmRepository.save(farmMapper.toFarm(farmRequestDTO));
    }

    @Override
    public void delete(Long id) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Farm with id '" + id + "' not found"));
        farmRepository.delete(farm);
    }

    @Override
    public Farm findById(Long id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Farm with id '" + id + "' not found"));
    }

    @Override
    public Farm update(Long id, FarmUpdateDto farmUpdateDto) {
        Farm existingFarm = farmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Farm with id '" + id + "' not found"));

        Farm updateFarm = farmMapper.partialUpdate(farmUpdateDto, existingFarm);
        return farmRepository.save(updateFarm);
    }

    @Override
    public Page<Farm> searchFarms(String name, String location, Double minArea, Double maxArea, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Farm> criteriaQuery = criteriaBuilder.createQuery(Farm.class);
        Root<Farm> root = criteriaQuery.from(Farm.class);

        List<Predicate> predicates = new ArrayList<>();
        if (name != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        }
        if (location != null) {
            predicates.add(criteriaBuilder.like(root.get("location"), "%" + location + "%"));
        }
        if (minArea != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("area"), minArea));
        }
        if (maxArea != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("area"), maxArea));
        }
        if (startDate != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate"), startDate));
        }
        if (endDate != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("creationDate"), endDate));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Farm> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Farm> farms = query.getResultList();
        long total = farms.size();
        return new PageImpl<>(farms, pageable, total);
    }
}
