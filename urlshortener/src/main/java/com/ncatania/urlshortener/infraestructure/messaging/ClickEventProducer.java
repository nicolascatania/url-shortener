package com.ncatania.urlshortener.infraestructure.messaging;

import com.ncatania.urlshortener.shared.ClickEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClickEventProducer {

    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE = "url-events";
    private static final String ROUTING_KEY = "click.event";

    public void publishClickEvent(ClickEventDTO event) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, event);
    }
}