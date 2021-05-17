package com.realthomasmiles.marketplace.service;

import com.realthomasmiles.marketplace.controller.v1.command.PostingFormCommand;
import com.realthomasmiles.marketplace.controller.v1.request.PostPostingRequest;
import com.realthomasmiles.marketplace.dto.model.marketplace.CategoryDto;
import com.realthomasmiles.marketplace.dto.model.marketplace.LocationDto;
import com.realthomasmiles.marketplace.dto.model.marketplace.PostingDto;
import com.realthomasmiles.marketplace.dto.model.user.UserDto;

import java.util.List;

public interface PostingService {

    Long getCountByCategory(CategoryDto categoryDto);

    Long getCountByLocation(LocationDto locationDto);

    List<PostingDto> getAllPostings();

    Long getNumberOfPostings();

    PostingDto getPostingById(Long id);

    void deletePostingById(Long id);

    List<PostingDto> getPostingsByNameContains(String text);

    List<PostingDto> getPostingsByLocation(LocationDto locationDto);

    List<PostingDto> getPostingsByCategory(CategoryDto categoryDto);

    List<PostingDto> getPostingsByUser(UserDto userDto);

    PostingDto postPosting(PostPostingRequest postPostingRequest, CategoryDto categoryDto, LocationDto locationDto,
                           UserDto userDto);

    PostingDto postPostingUI(PostingFormCommand postingFormCommand, CategoryDto categoryDto, LocationDto locationDto,
                             UserDto userDto, String fileName);

    CategoryDto getCategoryById(Long categoryId);

    LocationDto getLocationById(Long locationId);

}
