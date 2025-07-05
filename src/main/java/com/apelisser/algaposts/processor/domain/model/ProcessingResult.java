package com.apelisser.algaposts.processor.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProcessingResult {

    private int wordCount;
    private BigDecimal calculatedValue;

    public static ProcessingResult of(int wordCount, BigDecimal calculatedValue) {
        ProcessingResult result = new ProcessingResult();
        result.setWordCount(wordCount);
        result.setCalculatedValue(calculatedValue);
        return result;
    }

}
