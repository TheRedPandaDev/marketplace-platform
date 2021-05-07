package com.realthomasmiles.marketplace.service;

import com.realthomasmiles.marketplace.controller.v1.request.PostPostingRequest;
import com.realthomasmiles.marketplace.dto.mapper.PostingMapper;
import com.realthomasmiles.marketplace.dto.model.marketplace.CategoryDto;
import com.realthomasmiles.marketplace.dto.model.marketplace.PostingDto;
import com.realthomasmiles.marketplace.exception.EntityType;
import com.realthomasmiles.marketplace.exception.ExceptionType;
import com.realthomasmiles.marketplace.exception.MarketPlaceException;
import com.realthomasmiles.marketplace.model.marketplace.Category;
import com.realthomasmiles.marketplace.model.marketplace.Posting;
import com.realthomasmiles.marketplace.repository.marketplace.CategoryRepository;
import com.realthomasmiles.marketplace.repository.marketplace.PostingRepository;
import com.realthomasmiles.marketplace.util.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class PostingServiceImpl implements PostingService {

    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PostingDto> getAllPostings() {
        return StreamSupport.stream(postingRepository.findAll().spliterator(), false)
                .map(PostingMapper::toPostingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostingDto> getPostingsByNameContains(String text) {
        return postingRepository.findByNameContains(text).stream()
                .map(PostingMapper::toPostingDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostingDto postPosting(PostPostingRequest postPostingRequest, CategoryDto categoryDto) {
        Optional<Category> category = categoryRepository.findById(categoryDto.getId());
        if (category.isPresent()) {
            Posting posting = new Posting()
                    .setArticle("article")
                    .setIsActive(true)
                    .setCategory(category.get())
                    .setAuthorId(0L)
                    .setLocationId(postPostingRequest.getLocationId())
                    .setPosted(DateUtils.today())
                    .setName(postPostingRequest.getName())
                    .setDescription(postPostingRequest.getDescription())
                    .setPrice(postPostingRequest.getPrice());

            posting = postingRepository.save(posting);

            return PostingMapper.toPostingDto(posting);
        }

        throw exception(EntityType.CATEGORY, ExceptionType.ENTITY_NOT_FOUND, categoryDto.getId().toString());
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            return modelMapper.map(categoryOptional.get(), CategoryDto.class);
        }

        throw exception(EntityType.CATEGORY, ExceptionType.ENTITY_NOT_FOUND, categoryId.toString());
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return MarketPlaceException.throwException(entityType, exceptionType, args);
    }
}
