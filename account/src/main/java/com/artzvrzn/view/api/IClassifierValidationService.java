package com.artzvrzn.view.api;

import java.util.UUID;

public interface IClassifierValidationService {

    boolean isCurrencyPresent(UUID id);

    boolean isCategoryPresent(UUID id);

}
