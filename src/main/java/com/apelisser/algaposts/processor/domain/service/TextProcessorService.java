package com.apelisser.algaposts.processor.domain.service;

import com.apelisser.algaposts.processor.domain.model.ProcessingResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class TextProcessorService {

    private final WordCountService wordCountService;
    private final WordValueCalculatorService wordValueCalculatorService;

    public TextProcessorService(WordCountService wordCountService, WordValueCalculatorService wordValueCalculatorService) {
        this.wordCountService = wordCountService;
        this.wordValueCalculatorService = wordValueCalculatorService;
    }

    public ProcessingResult process(String text) {
        int numberOfWords = wordCountService.countWords(text);
        log.info("Number of words: {}", numberOfWords);

        BigDecimal calculatedValue = wordValueCalculatorService.calculateValue(numberOfWords);
        return ProcessingResult.of(numberOfWords, calculatedValue);
    }


}
