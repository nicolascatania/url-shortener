package com.ncatania.statisticsservice.application.ports.out;

import com.ncatania.statisticsservice.application.dto.TopClickedUrls;
import com.ncatania.statisticsservice.domain.model.ClickStatistic;

import java.util.List;

public interface ClickStatisticRepositoryPort {
    ClickStatistic save(ClickStatistic clickStatistic);
    List<ClickStatistic> findByUrlId(Long urlId);
    List<TopClickedUrls> getTopClickedUrls();
}
