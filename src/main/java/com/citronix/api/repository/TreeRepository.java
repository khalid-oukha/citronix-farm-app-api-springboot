package com.citronix.api.repository;

import com.citronix.api.domain.Field;
import com.citronix.api.domain.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TreeRepository extends JpaRepository<Tree, Long> {

    Long countByField(Field field);

    List<Tree> findByFieldAndPlantationDateAfter(Field field, LocalDateTime date);
}
