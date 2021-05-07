package com.realthomasmiles.marketplace.controller.v1.api;

import com.realthomasmiles.marketplace.controller.v1.request.PostPostingRequest;
import com.realthomasmiles.marketplace.dto.model.marketplace.CategoryDto;
import com.realthomasmiles.marketplace.dto.model.marketplace.LocationDto;
import com.realthomasmiles.marketplace.dto.response.Response;
import com.realthomasmiles.marketplace.dto.model.marketplace.PostingDto;
import com.realthomasmiles.marketplace.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/all")
    public Response<Object> getAllPostings() {
        return Response
                .ok()
                .setPayload(postingService.getAllPostings());
    }

    @GetMapping("/search")
    public Response<Object> searchPostingsByName(@RequestParam String q) {
        return Response
                .ok()
                .setPayload(postingService.getPostingsByNameContains(q));
    }

    @PostMapping("/new")
    public Response<Object> postPosting(@RequestBody @Valid PostPostingRequest postPostingRequest) {
        Optional<CategoryDto> categoryDto = Optional.ofNullable(postingService.getCategoryById(postPostingRequest.getCategoryId()));
        if (categoryDto.isPresent()) {
            Optional<LocationDto> locationDto = Optional.ofNullable(postingService.getLocationById(postPostingRequest.getLocationId()));
            if (locationDto.isPresent()) {
                Optional<PostingDto> postingDto = Optional.ofNullable(postingService.postPosting(postPostingRequest, categoryDto.get(), locationDto.get()));
                if (postingDto.isPresent()) {
                    return Response
                            .ok()
                            .setPayload(postingDto.get());
                }
            }
        }

        return Response
                .badRequest()
                .setErrors("Unable to process posting");
    }
}
