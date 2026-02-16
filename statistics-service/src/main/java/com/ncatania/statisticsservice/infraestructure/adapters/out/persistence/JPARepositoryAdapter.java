package com.ncatania.statisticsservice.infraestructure.adapters.out.persistence;

import com.ncatania.statisticsservice.application.dto.TopClickedUrls;
import com.ncatania.statisticsservice.application.ports.out.ClickStatisticRepositoryPort;
import com.ncatania.statisticsservice.domain.model.ClickStatistic;
import com.ncatania.statisticsservice.infraestructure.mapper.ClickStatisticMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JPARepositoryAdapter implements ClickStatisticRepositoryPort {

    private final SpringDataClickStatisticRepository repository;

    @Override
    public ClickStatistic save(ClickStatistic clickStatistic) {
        ClickStatisticEntity entity = ClickStatisticMapper.toEntity(clickStatistic);
        ClickStatisticEntity saved = repository.save(entity);
        return ClickStatisticMapper.toDomain(saved);
    }

    @Override
    public List<ClickStatistic> findByUrlId(Long urlId) {
        return repository.findByUrlId(urlId)
                .stream()
                .map(ClickStatisticMapper::toDomain)
                .toList();
    }

    @Override
    public List<TopClickedUrls> getTopClickedUrls() {
        return repository.getTopClickedUrls();
    }
}
