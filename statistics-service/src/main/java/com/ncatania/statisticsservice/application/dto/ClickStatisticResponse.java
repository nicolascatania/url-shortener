package com.ncatania.statisticsservice.application.dto;

import java.time.LocalDateTime;

public record ClickStatisticResponse(
        Long id,
        Long urlId,
        String shortCode,
        String ipAddress,
        String userAgent,
        LocalDateTime clickedAt
) {}
