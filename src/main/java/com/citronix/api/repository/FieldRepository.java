package com.citronix.api.repository;

import com.citronix.api.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldRepository extends JpaRepository<Field, Long> {
}
