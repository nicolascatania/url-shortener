package com.ncatania.statisticsservice.infraestructure.messaging;

import com.ncatania.statisticsservice.application.event.ClickEventDTO;
import com.ncatania.statisticsservice.application.ports.out.ClickStatisticRepositoryPort;
import com.ncatania.statisticsservice.domain.model.ClickStatistic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClickEventConsumerTest {

    @Mock
    private ClickStatisticRepositoryPort repository;

    @InjectMocks
    private ClickEventConsumer consumer;

    @Test
    void consumeClickEvent_shouldSaveStatistic() {
        ClickEventDTO event = new ClickEventDTO(
                1L,
                "abc123",
                "192.168.1.100",
                "Mozilla/5.0 (Windows NT 10.0)",
                LocalDateTime.now()
        );

        ClickStatistic saved = new ClickStatistic(
                1L, 1L, "abc123", "192.168.1.100", "Mozilla/5.0 (Windows NT 10.0)",
                event.getClickedAt(), LocalDateTime.now()
        );

        when(repository.save(any(ClickStatistic.class))).thenReturn(saved);

        consumer.consumeClickEvent(event);

        ArgumentCaptor<ClickStatistic> captor = ArgumentCaptor.forClass(ClickStatistic.class);
        verify(repository, times(1)).save(captor.capture());

        ClickStatistic captured = captor.getValue();
        assertEquals(1L, captured.urlId());
        assertEquals("abc123", captured.shortCode());
        assertEquals("192.168.1.100", captured.ipAddress());
    }
}
