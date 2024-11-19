package com.citronix.api.repository;

import com.citronix.api.domain.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<Farm, Long> {
    Boolean existsByName(String name);
    
}
