package com.citronix.api.repository;

import com.citronix.api.domain.Harvest;
import com.citronix.api.domain.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findAllByHarvest(Harvest harvest);

    Page<Sale> findAll(Pageable pageable);

}
