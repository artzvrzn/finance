package com.artzvrzn.view;

import com.artzvrzn.dao.api.CategoryRepository;
import com.artzvrzn.dao.api.entity.CategoryEntity;
import com.artzvrzn.exception.ValidationException;
import com.artzvrzn.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService extends ClassifierService<Category> {

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public Category get(UUID id) {
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ValidationException(String.format("Category with id %s doesn't exist", id));
        }
        return mapper.map(optional.get(), Category.class);
    }

    @Override
    public void create(Category dto) {
        CategoryEntity categoryEntity = generateBaseFields(mapper.map(dto, CategoryEntity.class));
        UUID existId = categoryRepository.getIdByTitle(dto.getTitle());
        if (existId != null) {
            throw new ValidationException("Such category already exists");
        }
        categoryRepository.save(categoryEntity);
    }

    @Override
    public Page<Category> get(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(e -> mapper.map(e, Category.class));
    }
}
