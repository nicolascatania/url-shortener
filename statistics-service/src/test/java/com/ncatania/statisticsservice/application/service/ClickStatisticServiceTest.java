package com.ncatania.statisticsservice.application.service;

import com.ncatania.statisticsservice.application.dto.ClickStatisticResponse;
import com.ncatania.statisticsservice.application.dto.TopClickedUrls;
import com.ncatania.statisticsservice.application.dto.TopUrlStatisticResponse;
import com.ncatania.statisticsservice.application.ports.out.ClickStatisticRepositoryPort;
import com.ncatania.statisticsservice.domain.model.ClickStatistic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClickStatisticServiceTest {

    @Mock
    private ClickStatisticRepositoryPort repository;

    @InjectMocks
    private ClickStatisticService service;

    @Test
    void getStatisticsByUrlId_shouldReturnClickStats() {
        Long urlId = 1L;
        ClickStatistic stat1 = new ClickStatistic(1L, urlId, "abc123", "192.168.1.1", "Chrome",
                LocalDateTime.now(), LocalDateTime.now());
        ClickStatistic stat2 = new ClickStatistic(2L, urlId, "abc123", "192.168.1.2", "Firefox",
                LocalDateTime.now(), LocalDateTime.now());

        when(repository.findByUrlId(urlId)).thenReturn(List.of(stat1, stat2));

        List<ClickStatisticResponse> result = service.getStatisticsByUrlId(urlId);

        assertEquals(2, result.size());
        assertEquals("abc123", result.get(0).shortCode());
        verify(repository, times(1)).findByUrlId(urlId);
    }

    @Test
    void getTopClickedUrls_shouldReturnRanking() {
        List<TopClickedUrls> mockData = List.of(
                new TopClickedUrls("shortcode1", 150L),
                new TopClickedUrls("shortcode2", 100L),
                new TopClickedUrls("shortcode2", 10L)
        );

        when(repository.getTopClickedUrls()).thenReturn(mockData);

        List<TopUrlStatisticResponse> result = service.getTopClickedUrls();

        assertEquals(3, result.size());
        assertEquals("shortcode1", result.get(0).shortCode());
        assertEquals(150L, result.get(0).clickCount());
        assertEquals(1, result.get(0).rank());
        assertEquals(2, result.get(1).rank());
        assertEquals(3, result.get(2).rank());
        verify(repository, times(1)).getTopClickedUrls();
    }
}
