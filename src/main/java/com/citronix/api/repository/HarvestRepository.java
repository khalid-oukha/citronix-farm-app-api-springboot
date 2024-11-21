package com.citronix.api.repository;

import com.citronix.api.domain.Field;
import com.citronix.api.domain.Harvest;
import com.citronix.api.domain.enums.SeasonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HarvestRepository extends JpaRepository<Harvest, Long> {

    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Harvest h " +
            "JOIN h.harvestDetails hd " +
            "JOIN hd.tree t " +
            "WHERE t.field = :field " +
            "AND h.season = :season " +
            "AND YEAR(h.harvestDate) = :year")
    boolean existsByFieldAndSeasonAndHarvestDateYear(@Param("field") Field field,
                                                     @Param("season") SeasonType season,
                                                     @Param("year") int year);
}
