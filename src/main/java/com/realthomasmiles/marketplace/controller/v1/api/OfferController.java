package com.realthomasmiles.marketplace.controller.v1.api;

import com.realthomasmiles.marketplace.controller.v1.request.MakeOfferRequest;
import com.realthomasmiles.marketplace.dto.model.marketplace.PostingDto;
import com.realthomasmiles.marketplace.dto.model.user.UserDto;
import com.realthomasmiles.marketplace.dto.response.Response;
import com.realthomasmiles.marketplace.service.OfferService;
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
@RequestMapping("/api/v1/offer")
@Validated
public class OfferController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private PostingService postingService;

    @Autowired
    private UserService userService;

    @GetMapping("/postingOffers")
    public Response<Object> getOffersByPosting(@RequestParam(name = "posting") Long postingId) {
        Optional<PostingDto> postingDto = Optional.ofNullable(postingService.getPostingById(postingId));
        if (postingDto.isPresent()) {
            return Response
                    .ok()
                    .setPayload(offerService.getOffersByPosting(postingDto.get()));
        }

        return Response
                .ok()
                .setErrors("Unable to process request");
    }

    @GetMapping("/myOffers")
    public Response<Object> getCurrentUsersOffers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        Optional<UserDto> userDto = Optional.ofNullable(userService.findUserByEmail(email));
        if (userDto.isPresent()) {
            return Response
                    .ok()
                    .setPayload(offerService.getOffersByUser(userDto.get()));
        }

        return Response
                .badRequest()
                .setErrors("Unable to process request");
    }

    @GetMapping("/myOffersByPosting")
    public Response<Object> getCurrentUsersOffersByPosting(@RequestParam(name = "posting") Long postingId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        Optional<UserDto> userDto = Optional.ofNullable(userService.findUserByEmail(email));
        if (userDto.isPresent()) {
            Optional<PostingDto> postingDto = Optional.ofNullable(postingService.getPostingById(postingId));
            if (postingDto.isPresent()) {
                return Response
                        .ok()
                        .setPayload(offerService.getOffersByPostingAndUser(postingDto.get(), userDto.get()));
            }
        }

        return Response
                .badRequest()
                .setErrors("Unable to process request");
    }

    @PostMapping("/makeOffer")
    public Response<Object> makeOffer(@RequestBody @Valid MakeOfferRequest makeOfferRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        Optional<UserDto> userDto = Optional.ofNullable(userService.findUserByEmail(email));
        if (userDto.isPresent()) {
            Optional<PostingDto> postingDto = Optional.ofNullable(postingService.getPostingById(makeOfferRequest.getPostingId()));
            if (postingDto.isPresent()) {
                return Response
                        .ok()
                        .setPayload(offerService.makeOffer(makeOfferRequest, userDto.get()));
            }
        }

        return Response
                .badRequest()
                .setErrors("Unable to process request");
    }

}
