package com.apelisser.algaposts.processor.domain.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WordValueCalculatorService {

    private final WordValueProvider wordValueProvider;

    public WordValueCalculatorService(WordValueProvider wordValueProvider) {
        this.wordValueProvider = wordValueProvider;
    }

    public BigDecimal calculateValue(int quantityOfWord) {
        if (quantityOfWord <= 0) {
            return BigDecimal.ZERO;
        }

        return wordValueProvider.getWordValue().multiply(BigDecimal.valueOf(quantityOfWord));
    }

}
