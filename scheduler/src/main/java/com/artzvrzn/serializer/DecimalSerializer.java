package com.artzvrzn.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class DecimalSerializer extends JsonSerializer<Double> {

    @Override
    public void serialize(Double d, JsonGenerator generator, SerializerProvider provider) throws IOException {
            generator.writeNumber(String.format("%.0f", d));
    }
}
