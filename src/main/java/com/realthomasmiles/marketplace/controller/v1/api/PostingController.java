package com.realthomasmiles.marketplace.controller.v1.api;

import com.realthomasmiles.marketplace.controller.v1.request.PostPostingRequest;
import com.realthomasmiles.marketplace.dto.model.marketplace.CategoryDto;
import com.realthomasmiles.marketplace.dto.model.marketplace.LocationDto;
import com.realthomasmiles.marketplace.dto.model.user.UserDto;
import com.realthomasmiles.marketplace.dto.response.Response;
import com.realthomasmiles.marketplace.dto.model.marketplace.PostingDto;
import com.realthomasmiles.marketplace.service.CategoryService;
import com.realthomasmiles.marketplace.service.LocationService;
import com.realthomasmiles.marketplace.service.PostingService;
import com.realthomasmiles.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posting")
@Validated
public class PostingController {

    @Autowired
    private PostingService postingService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public Response<Object> getAllPostings() {
        return Response
                .ok()
                .setPayload(postingService.getAllPostings());
    }

    @GetMapping("/search")
    public Response<Object> searchPostingsByName(@RequestParam(name = "q") String text) {
        return Response
                .ok()
                .setPayload(postingService.getPostingsByNameContains(text));
    }

    @GetMapping("/locationPostings")
    public Response<Object> getPostingsByLocation(@RequestParam(name = "location") String locationName) {
        Optional<LocationDto> locationDto = Optional.ofNullable(locationService.getLocationByName(locationName));
        if (locationDto.isPresent()) {
            return Response
                    .ok()
                    .setPayload(postingService.getPostingsByLocation(locationDto.get()));
        }

        return Response
                .badRequest()
                .setErrors("Unable to process request");
    }

    @GetMapping("/categoryPostings")
    public Response<Object> getPostingsByCategory(@RequestParam(name = "category") String categoryName) {
        Optional<CategoryDto> categoryDto = Optional.ofNullable(categoryService.getCategoryByName(categoryName));
        if (categoryDto.isPresent()) {
            return Response
                    .ok()
                    .setPayload(postingService.getPostingsByCategory(categoryDto.get()));
        }

        return Response
                .badRequest()
                .setErrors("Unable to process request");
    }

    @GetMapping("/myPostings")
    public Response<Object> getCurrentUsersPostings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        Optional<UserDto> userDto = Optional.ofNullable(userService.findUserByEmail(email));
        if (userDto.isPresent()) {
            return Response
                    .ok()
                    .setPayload(postingService.getPostingsByUser(userDto.get()));
        }

        return Response
                .badRequest()
                .setErrors("Unable to process request");
    }

    @GetMapping("/userPostings")
    public Response<Object> getUsersPostings(@RequestParam(name = "user") String email) {
        Optional<UserDto> userDto = Optional.ofNullable(userService.findUserByEmail(email));
        if (userDto.isPresent()) {
            return Response
                    .ok()
                    .setPayload(postingService.getPostingsByUser(userDto.get()));
        }

        return Response
                .badRequest()
                .setErrors("Unable to process request");
    }

    @PostMapping("/new")
    public Response<Object> postPosting(@RequestBody @Valid PostPostingRequest postPostingRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        Optional<UserDto> userDto = Optional.ofNullable(userService.findUserByEmail(email));
        if (userDto.isPresent()) {
            Optional<CategoryDto> categoryDto = Optional.ofNullable(postingService.getCategoryById(postPostingRequest.getCategoryId()));
            if (categoryDto.isPresent()) {
                Optional<LocationDto> locationDto = Optional.ofNullable(postingService.getLocationById(postPostingRequest.getLocationId()));
                if (locationDto.isPresent()) {
                    Optional<PostingDto> postingDto = Optional.ofNullable(postingService.postPosting(postPostingRequest, categoryDto.get(), locationDto.get(), userDto.get()));
                    if (postingDto.isPresent()) {
                        return Response
                                .ok()
                                .setPayload(postingDto.get());
                    }
                }
            }
        }

        return Response
                .badRequest()
                .setErrors("Unable to process posting");
    }
}
