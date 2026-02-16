package com.ncatania.statisticsservice.application.dto;

public record TopClickedUrls(
        String shortCode,
        Long clickCount
) {
}
