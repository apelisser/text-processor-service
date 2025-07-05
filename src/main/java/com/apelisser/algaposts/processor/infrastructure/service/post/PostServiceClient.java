package com.apelisser.algaposts.processor.infrastructure.service.post;

import com.apelisser.algaposts.processor.core.rabbitmq.RabbitMQConfig;
import com.apelisser.algaposts.processor.domain.model.ProcessingResult;
import com.apelisser.algaposts.processor.domain.service.TextProcessorService;
import com.apelisser.algaposts.processor.infrastructure.service.post.model.PostStatsInput;
import com.apelisser.algaposts.processor.infrastructure.service.post.model.PostStatsOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.apelisser.algaposts.processor.core.rabbitmq.RabbitMQConfig.DIRECT_EXCHANGE_POST_PROCESSING;
import static com.apelisser.algaposts.processor.core.rabbitmq.RabbitMQConfig.ROUTING_KEY_POST_PROCESSING_RESULT;

@Slf4j
@Component
public class PostServiceClient {

    private final TextProcessorService textProcessorService;
    private final RabbitTemplate rabbitTemplate;

    public PostServiceClient(TextProcessorService textProcessorService, RabbitTemplate rabbitTemplate) {
        this.textProcessorService = textProcessorService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_POST_PROCESSING, concurrency = "2-5")
    private void updatePostStats(PostStatsInput postInput) {
        log.info("Processing stats for postId: {}", postInput.postId());
        ProcessingResult processingResult = textProcessorService.process(postInput.postBody());

        PostStatsOutput postStats = PostStatsOutput.of(
            postInput.postId(),
            processingResult.getWordCount(),
            processingResult.getCalculatedValue());

        log.info("Sending stats for postId: {}", postInput.postId());
        rabbitTemplate.convertAndSend(DIRECT_EXCHANGE_POST_PROCESSING, ROUTING_KEY_POST_PROCESSING_RESULT, postStats);
    }

}
