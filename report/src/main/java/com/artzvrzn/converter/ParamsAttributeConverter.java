package com.artzvrzn.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Converter(autoApply = true)
public class ParamsAttributeConverter implements AttributeConverter<Map<String, Object>, String> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(Map<String, Object> params) {
        try {
            return mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to convert params to entity", e);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String params) {
        try {
            return mapper.readValue(params, new TypeReference<HashMap<String,Object>>() {});
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to convert params from entity to dto", e);
        }
    }
}
