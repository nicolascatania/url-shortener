package com.ncatania.statisticsservice.infraestructure.messaging;

import com.ncatania.statisticsservice.application.event.ClickEventDTO;
import com.ncatania.statisticsservice.application.ports.out.ClickStatisticRepositoryPort;
import com.ncatania.statisticsservice.domain.model.ClickStatistic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClickEventConsumer {

    private final ClickStatisticRepositoryPort clickStatisticRepository;

    @RabbitListener(queues = "click-events")
    @Transactional
    public void consumeClickEvent(ClickEventDTO event) {
        log.info("📊 Event received: URL ID={}, Short Code={}, IP={}",
                event.getUrlId(), event.getShortCode(), event.getIpAddress());

        ClickStatistic statistic = new ClickStatistic(
                null,
                event.getUrlId(),
                event.getShortCode(),
                event.getIpAddress(),
                event.getUserAgent(),
                event.getClickedAt(),
                LocalDateTime.now()
        );

        ClickStatistic saved = clickStatisticRepository.save(statistic);
        log.info("✅ Statistic saved: ID={}", saved.id());
    }
}
