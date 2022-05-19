package com.artzvrzn.dao.api.converter;

import com.artzvrzn.model.report.ReportParam;
import org.springframework.util.SerializationUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ParamsAttributeConverter implements AttributeConverter<ReportParam, Byte[]> {

    @Override
    public Byte[] convertToDatabaseColumn(ReportParam params) {
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
    public ReportParam convertToEntityAttribute(Byte[] byteObjects) {
        byte[] bytes = new byte[byteObjects.length];
        for (int i = 0; i < byteObjects.length; i++) {
            bytes[i] = byteObjects[i].byteValue();
        }
        return (ReportParam) SerializationUtils.deserialize(bytes);
    }
}
