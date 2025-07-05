package com.apelisser.algaposts.processor.domain.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WordCountService {

    public int countWords(String content) {
        if (content == null || content.isBlank()) {
            return 0;
        }

        Pattern wordPattern = Pattern.compile("\\p{L}+");
        Matcher matcher = wordPattern.matcher(content);
        return (int) matcher.results().count();
    }

}
