package com.realthomasmiles.marketplace.service;

import com.realthomasmiles.marketplace.controller.v1.command.PostingFormCommand;
import com.realthomasmiles.marketplace.controller.v1.request.PostPostingRequest;
import com.realthomasmiles.marketplace.dto.mapper.PostingMapper;
import com.realthomasmiles.marketplace.dto.model.marketplace.CategoryDto;
import com.realthomasmiles.marketplace.dto.model.marketplace.LocationDto;
import com.realthomasmiles.marketplace.dto.model.marketplace.PostingDto;
import com.realthomasmiles.marketplace.dto.model.user.UserDto;
import com.realthomasmiles.marketplace.exception.EntityType;
import com.realthomasmiles.marketplace.exception.ExceptionType;
import com.realthomasmiles.marketplace.exception.MarketPlaceException;
import com.realthomasmiles.marketplace.model.marketplace.Category;
import com.realthomasmiles.marketplace.model.marketplace.Location;
import com.realthomasmiles.marketplace.model.marketplace.Posting;
import com.realthomasmiles.marketplace.model.user.User;
import com.realthomasmiles.marketplace.repository.marketplace.CategoryRepository;
import com.realthomasmiles.marketplace.repository.marketplace.LocationRepository;
import com.realthomasmiles.marketplace.repository.marketplace.PostingRepository;
import com.realthomasmiles.marketplace.repository.user.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PostingDto> getAllPostings() {
        return StreamSupport.stream(postingRepository.findAll().spliterator(), false)
                .map(PostingMapper::toPostingDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long getNumberOfPostings() {
        return postingRepository.count();
    }

    @Override
    public PostingDto getPostingById(Long id) {
        Optional<Posting> posting = postingRepository.findById(id);
        if (posting.isPresent()) {
            return PostingMapper.toPostingDto(posting.get());
        }

        throw exception(EntityType.POSTING, ExceptionType.ENTITY_NOT_FOUND, id.toString());
    }

    @Override
    public List<PostingDto> getPostingsByUser(UserDto userDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            return postingRepository.findByAuthorId(user.get().getId()).stream()
                    .map(PostingMapper::toPostingDto)
                    .collect(Collectors.toList());
        }

        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userDto.getEmail());
    }

    @Override
    public List<PostingDto> getPostingsByNameContains(String text) {
        return postingRepository.findByNameContainsIgnoreCase(text).stream()
                .map(PostingMapper::toPostingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostingDto> getPostingsByLocation(LocationDto locationDto) {
        Optional<Location> location = locationRepository.findById(locationDto.getId());
        if (location.isPresent()) {
            return postingRepository.findByLocationId(location.get().getId()).stream()
                    .map(PostingMapper::toPostingDto)
                    .collect(Collectors.toList());
        }

        throw exception(EntityType.LOCATION, ExceptionType.ENTITY_NOT_FOUND, locationDto.getId().toString());
    }

    @Override
    public List<PostingDto> getPostingsByCategory(CategoryDto categoryDto) {
        Optional<Category> category = categoryRepository.findById(categoryDto.getId());
        if (category.isPresent()) {
            return postingRepository.findByLocationId(category.get().getId()).stream()
                    .map(PostingMapper::toPostingDto)
                    .collect(Collectors.toList());
        }

        throw exception(EntityType.CATEGORY, ExceptionType.ENTITY_NOT_FOUND, categoryDto.getId().toString());
    }

    @Override
    public PostingDto postPosting(PostPostingRequest postPostingRequest, CategoryDto categoryDto, LocationDto locationDto,
                                  UserDto userDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            Optional<Category> category = categoryRepository.findById(categoryDto.getId());
            if (category.isPresent()) {
                Optional<Location> location = locationRepository.findById(locationDto.getId());
                if (location.isPresent()) {
                    Posting posting = new Posting()
                            .setArticle("article")
                            .setIsActive(true)
                            .setCategory(category.get())
                            .setAuthor(user.get())
                            .setLocation(location.get())
                            .setPosted(DateUtils.today())
                            .setName(postPostingRequest.getName())
                            .setDescription(postPostingRequest.getDescription())
                            .setPrice(postPostingRequest.getPrice());

                    posting = postingRepository.save(posting);

                    return PostingMapper.toPostingDto(posting);
                }

                throw exception(EntityType.LOCATION, ExceptionType.ENTITY_NOT_FOUND, locationDto.getId().toString());
            }

            throw exception(EntityType.CATEGORY, ExceptionType.ENTITY_NOT_FOUND, categoryDto.getId().toString());
        }

        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userDto.getEmail());
    }

    @Override
    public PostingDto postPostingUI(PostingFormCommand postingFormCommand, CategoryDto categoryDto, LocationDto locationDto,
                                    UserDto userDto, String fileName) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            Optional<Category> category = categoryRepository.findById(categoryDto.getId());
            if (category.isPresent()) {
                Optional<Location> location = locationRepository.findById(locationDto.getId());
                if (location.isPresent()) {
                    Posting posting = new Posting()
                            .setArticle("article")
                            .setPhoto(fileName)
                            .setIsActive(true)
                            .setCategory(category.get())
                            .setAuthor(user.get())
                            .setLocation(location.get())
                            .setPosted(DateUtils.today())
                            .setName(postingFormCommand.getName())
                            .setDescription(postingFormCommand.getDescription())
                            .setPrice(postingFormCommand.getPrice());

                    posting = postingRepository.save(posting);

                    return PostingMapper.toPostingDto(posting);
                }

                throw exception(EntityType.LOCATION, ExceptionType.ENTITY_NOT_FOUND, locationDto.getId().toString());
            }

            throw exception(EntityType.CATEGORY, ExceptionType.ENTITY_NOT_FOUND, categoryDto.getId().toString());
        }

        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userDto.getEmail());
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            return modelMapper.map(categoryOptional.get(), CategoryDto.class);
        }

        throw exception(EntityType.CATEGORY, ExceptionType.ENTITY_NOT_FOUND, categoryId.toString());
    }

    @Override
    public LocationDto getLocationById(Long locationId) {
        Optional<Location> locationOptional = locationRepository.findById(locationId);
        if (locationOptional.isPresent()) {
            return modelMapper.map(locationOptional.get(), LocationDto.class);
        }

        throw exception(EntityType.LOCATION, ExceptionType.ENTITY_NOT_FOUND, locationId.toString());
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return MarketPlaceException.throwException(entityType, exceptionType, args);
    }
}
