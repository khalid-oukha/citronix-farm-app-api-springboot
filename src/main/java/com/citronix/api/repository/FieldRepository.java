package com.citronix.api.repository;

import com.citronix.api.domain.Farm;
import com.citronix.api.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {
    Long countByFarm(Farm farm);

    List<Field> findByFarm(Farm farm);
}
