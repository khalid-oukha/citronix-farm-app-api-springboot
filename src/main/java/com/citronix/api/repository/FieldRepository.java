package com.citronix.api.repository;

import com.citronix.api.domain.Farm;
import com.citronix.api.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FieldRepository extends JpaRepository<Field, Long> {
    Long countByFarm(Farm farm);

    @Query("SELECT SUM(f.area) FROM Field f WHERE f.farm = :farm")
    double sumAreaByFarm(Farm farm);
}
