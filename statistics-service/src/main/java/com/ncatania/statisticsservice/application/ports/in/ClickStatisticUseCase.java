package com.ncatania.statisticsservice.application.ports.in;

import com.ncatania.statisticsservice.application.dto.ClickStatisticResponse;
import com.ncatania.statisticsservice.application.dto.TopUrlStatisticResponse;

import java.util.List;

public interface ClickStatisticUseCase {
    List<ClickStatisticResponse> getStatisticsByUrlId(Long urlId);
    List<TopUrlStatisticResponse> getTopClickedUrls();
}
