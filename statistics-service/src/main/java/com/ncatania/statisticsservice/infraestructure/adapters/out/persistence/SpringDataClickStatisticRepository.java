package com.ncatania.statisticsservice.infraestructure.adapters.out.persistence;

import com.ncatania.statisticsservice.application.dto.TopClickedUrls;
import com.ncatania.statisticsservice.domain.model.ClickStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataClickStatisticRepository extends JpaRepository<ClickStatisticEntity, Long> {


    List<ClickStatisticEntity> findByUrlId(Long urlId);

    @Query("SELECT new com.ncatania.statisticsservice.application.dto.TopClickedUrls(cs.shortCode, COUNT(cs)) " +
            "FROM ClickStatisticEntity cs " +
            "GROUP BY cs.shortCode " +
            "ORDER BY COUNT(cs) DESC")
    List<TopClickedUrls> getTopClickedUrls();
}
