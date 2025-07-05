package com.apelisser.algaposts.processor.core.wordpricing;

import com.apelisser.algaposts.processor.domain.service.WordValueProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConfigurableWordValueProvider implements WordValueProvider {

    @Value("${pricing.word.unit}")
    private BigDecimal wordValue;

    @Override
    public BigDecimal getWordValue() {
        return wordValue;
    }

}
