package com.ncatania.statisticsservice.application.dto;

public record TopUrlStatisticResponse(
        String shortCode,
        Long clickCount,
        Integer rank
) {}
