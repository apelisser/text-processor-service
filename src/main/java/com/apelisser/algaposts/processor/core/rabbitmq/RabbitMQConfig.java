package com.apelisser.algaposts.processor.core.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String DIRECT_EXCHANGE_POST_PROCESSING = "text-processor-service.post-processing.v1.e";

    public static final String QUEUE_POST_PROCESSING = "text-processor-service.post-processing.v1.q";
    public static final String QUEUE_POST_PROCESSING_DEAD_LETTER = "text-processor-service.post-processing.v1.dlq";

    public static final String ROUTING_KEY_POST_PROCESSING = "text-processor-service.post-processing.v1.r";
    public static final String ROUTING_KEY_POST_PROCESSING_RESULT = "text-processor-service.post-processing-result.v1.r";

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public DirectExchange directExchangePostProcessing() {
        return ExchangeBuilder
            .directExchange(DIRECT_EXCHANGE_POST_PROCESSING)
            .build();
    }

    @Bean
    public Queue queuePostProcessing() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "");
        args.put("x-dead-letter-routing-key", QUEUE_POST_PROCESSING_DEAD_LETTER);

        return QueueBuilder
            .durable(QUEUE_POST_PROCESSING)
            .withArguments(args)
            .build();
    }

    @Bean
    public Queue deadLetterQueuePostProcessing() {
        return QueueBuilder
            .durable(QUEUE_POST_PROCESSING_DEAD_LETTER)
            .build();
    }

    @Bean
    public Binding bindingPostProcessing(DirectExchange directExchangePostProcessing, Queue queuePostProcessing) {
        return BindingBuilder
            .bind(queuePostProcessing)
            .to(directExchangePostProcessing)
            .with(ROUTING_KEY_POST_PROCESSING);
    }

}
