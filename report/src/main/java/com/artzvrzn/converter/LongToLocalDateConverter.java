package com.artzvrzn.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Component
public class LongToLocalDateConverter implements Converter<Long, LocalDate> {

    @Override
    public LocalDate convert(Long source) {
        return LocalDate.ofInstant(Instant.ofEpochMilli(source), ZoneOffset.UTC);
    }
}
