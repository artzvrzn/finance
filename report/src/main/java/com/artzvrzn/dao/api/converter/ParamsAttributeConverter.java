package com.artzvrzn.dao.api.converter;

import com.artzvrzn.model.Params;
import org.springframework.util.SerializationUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ParamsAttributeConverter implements AttributeConverter<Params, Byte[]> {

    @Override
    public Byte[] convertToDatabaseColumn(Params params) {
        byte[] bytes = SerializationUtils.serialize(params);
        if (bytes == null) {
            throw new IllegalStateException("Cannot serialize Param to bytes");
        }
        Byte[] byteObjects = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            byteObjects[i] = bytes[i];
        }
        return byteObjects;
    }

    @Override
    public Params convertToEntityAttribute(Byte[] byteObjects) {
        byte[] bytes = new byte[byteObjects.length];
        for (int i = 0; i < byteObjects.length; i++) {
            bytes[i] = byteObjects[i].byteValue();
        }
        return (Params) SerializationUtils.deserialize(bytes);
    }
}
