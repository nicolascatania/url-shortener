package com.ncatania.statisticsservice.domain.model;


import java.time.LocalDateTime;

public record ClickStatistic(Long id, Long urlId, String shortCode, String ipAddress, String userAgent,
                             LocalDateTime clickedAt,
                             LocalDateTime createdAt
) {
}
