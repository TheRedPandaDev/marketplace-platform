package com.realthomasmiles.marketplace.service;

import com.realthomasmiles.marketplace.dto.mapper.CategoryMapper;
import com.realthomasmiles.marketplace.dto.model.marketplace.CategoryDto;
import com.realthomasmiles.marketplace.exception.EntityType;
import com.realthomasmiles.marketplace.exception.ExceptionType;
import com.realthomasmiles.marketplace.exception.MarketPlaceException;
import com.realthomasmiles.marketplace.model.marketplace.Category;
import com.realthomasmiles.marketplace.repository.marketplace.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CategoryDto> getAllCategories() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        Optional<Category> category = Optional.ofNullable(categoryRepository.findByNameIgnoreCase(name));
        if (category.isPresent()) {
            return modelMapper.map(category.get(), CategoryDto.class);
        }

        throw exception(EntityType.CATEGORY, ExceptionType.ENTITY_NOT_FOUND, name);
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return MarketPlaceException.throwException(entityType, exceptionType, args);
    }

}
