package com.artzvrzn.converter;

import com.artzvrzn.model.Operation;
import com.artzvrzn.model.OuterOperation;
import com.artzvrzn.util.TimeUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OperationToOuterOperationConverter implements Converter<Operation, OuterOperation> {

    @Override
    public OuterOperation convert(Operation source) {
        OuterOperation outerOperation = new OuterOperation();
        outerOperation.setDate(TimeUtils.getCurrentSeconds());
        outerOperation.setCategory(source.getCategory());
        outerOperation.setCurrency(source.getCurrency());
        outerOperation.setValue(source.getValue());
        outerOperation.setDescription(source.getDescription());
        return outerOperation;
    }
}
