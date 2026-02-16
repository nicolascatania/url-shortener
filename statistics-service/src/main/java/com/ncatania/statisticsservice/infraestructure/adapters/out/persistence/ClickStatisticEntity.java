package com.ncatania.statisticsservice.infraestructure.adapters.out.persistence;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "click_statistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClickStatisticEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long urlId;

    @Column(nullable = false)
    private String shortCode;

    @Column
    private String ipAddress;

    @Column
    private String userAgent;

    @Column(nullable = false)
    private LocalDateTime clickedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}