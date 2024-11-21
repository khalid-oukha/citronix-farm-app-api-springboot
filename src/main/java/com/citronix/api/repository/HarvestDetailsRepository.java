package com.citronix.api.repository;

import com.citronix.api.domain.HarvestDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HarvestDetailsRepository extends JpaRepository<HarvestDetail, Long> {
}
