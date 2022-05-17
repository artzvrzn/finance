package com.artzvrzn.view;

import com.artzvrzn.dao.api.CurrencyRepository;
import com.artzvrzn.dao.api.entity.CurrencyEntity;
import com.artzvrzn.exception.ValidationException;
import com.artzvrzn.model.Currency;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CurrencyService extends ClassifierService<Currency> {

    private final CurrencyRepository currencyRepository;
    private final ModelMapper mapper;

    public CurrencyService(CurrencyRepository currencyRepository, ModelMapper mapper) {
        this.currencyRepository = currencyRepository;
        this.mapper = mapper;
    }

    @Override
    public void create(Currency dto) {
        CurrencyEntity entity = generateBaseFields(mapper.map(dto, CurrencyEntity.class));
        UUID existId = currencyRepository.getIdByTitle(dto.getTitle());
        if (existId != null) {
            throw new ValidationException("Such currency already exists");
        }
        currencyRepository.save(entity);
    }

    @Override
    public Currency get(UUID id) {
        Optional<CurrencyEntity> optional = currencyRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ValidationException(String.format("Currency with id %s doesn't exist", id));
        }
        return mapper.map(optional.get(), Currency.class);
    }

    @Override
    public Page<Currency> get(Pageable pageable) {
        return currencyRepository.findAll(pageable).map(e -> mapper.map(e, Currency.class));
    }
}
