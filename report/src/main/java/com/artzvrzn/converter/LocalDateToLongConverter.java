package com.artzvrzn.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;

@Component
public class LocalDateToLongConverter implements Converter<LocalDate, Long> {

    @Override
    public Long convert(LocalDate source) {
        return source.atStartOfDay(ZoneOffset.UTC).toEpochSecond();
    }
}
