package com.ncatania.statisticsservice.infraestructure.mapper;

import com.ncatania.statisticsservice.domain.model.ClickStatistic;
import com.ncatania.statisticsservice.infraestructure.adapters.out.persistence.ClickStatisticEntity;

public final class ClickStatisticMapper {

    private ClickStatisticMapper() {}

    public static ClickStatistic toDomain(ClickStatisticEntity entity) {
        if (entity == null) return null;
        return new ClickStatistic(
                entity.getId(),
                entity.getUrlId(),
                entity.getShortCode(),
                entity.getIpAddress(),
                entity.getUserAgent(),
                entity.getClickedAt(),
                entity.getCreatedAt()
        );
    }

    public static ClickStatisticEntity toEntity(ClickStatistic domain) {
        if (domain == null) return null;
        ClickStatisticEntity entity = new ClickStatisticEntity();
        entity.setId(domain.id());
        entity.setUrlId(domain.urlId());
        entity.setShortCode(domain.shortCode());
        entity.setIpAddress(domain.ipAddress());
        entity.setUserAgent(domain.userAgent());
        entity.setClickedAt(domain.clickedAt());
        entity.setCreatedAt(domain.createdAt());
        return entity;
    }
}
