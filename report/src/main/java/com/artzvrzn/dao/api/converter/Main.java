package com.artzvrzn.dao.api.converter;

import com.artzvrzn.model.Params;
import com.artzvrzn.model.ReportParamBalance;
import org.springframework.util.SerializationUtils;

import java.util.List;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        ParamsAttributeConverter paramAttributeConverter = new ParamsAttributeConverter();
        ReportParamBalance params = new ReportParamBalance();
        params.setAccounts(List.of(UUID.randomUUID(), UUID.randomUUID()));
        System.out.println(params.getAccounts());
        byte[] bytes = SerializationUtils.serialize(params);
        ReportParamBalance deserParams = (ReportParamBalance) SerializationUtils.deserialize(bytes);
        System.out.println(deserParams.getAccounts());
    }
}
