package com.ncatania.statisticsservice.application.service;

import com.ncatania.statisticsservice.application.dto.ClickStatisticResponse;
import com.ncatania.statisticsservice.application.dto.TopClickedUrls;
import com.ncatania.statisticsservice.application.dto.TopUrlStatisticResponse;
import com.ncatania.statisticsservice.application.ports.in.ClickStatisticUseCase;
import com.ncatania.statisticsservice.application.ports.out.ClickStatisticRepositoryPort;
import com.ncatania.statisticsservice.domain.model.ClickStatistic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClickStatisticService implements ClickStatisticUseCase {

    private final ClickStatisticRepositoryPort repository;

    @Override
    public List<ClickStatisticResponse> getStatisticsByUrlId(Long urlId) {
        return repository.findByUrlId(urlId)
                .stream()
                .map(domain -> new ClickStatisticResponse(
                        domain.id(),
                        domain.urlId(),
                        domain.shortCode(),
                        domain.ipAddress(),
                        domain.userAgent(),
                        domain.clickedAt()
                ))
                .toList();
    }

    @Override
    public List<TopUrlStatisticResponse> getTopClickedUrls() {
        List<TopClickedUrls> results = repository.getTopClickedUrls();
        return results.stream()
                .map(cs -> new TopUrlStatisticResponse(
                       cs.shortCode() ,  // shortCode
                        cs.clickCount(),  // clickCount
                        results.indexOf(cs) + 1  // rank (1-based)
                ))
                .toList();
    }
}
