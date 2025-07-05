package com.apelisser.algaposts.processor.infrastructure.service.post.model;

import java.math.BigDecimal;

public record PostStatsOutput(
    String postId,
    Integer wordCount,
    BigDecimal calculatedValue) {

    public static PostStatsOutput of(String postId, Integer wordCount, BigDecimal calculatedValue) {
        return new PostStatsOutput(postId, wordCount, calculatedValue);
    }

}
