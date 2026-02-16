package com.ncatania.statisticsservice.application.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClickEventDTO {
    private Long urlId;
    private String shortCode;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime clickedAt;
}
