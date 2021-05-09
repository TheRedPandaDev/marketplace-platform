package com.realthomasmiles.marketplace.controller.v1.api;

import com.realthomasmiles.marketplace.controller.v1.request.MakeSaleRequest;
import com.realthomasmiles.marketplace.dto.model.marketplace.PostingDto;
import com.realthomasmiles.marketplace.dto.model.user.UserDto;
import com.realthomasmiles.marketplace.dto.response.Response;
import com.realthomasmiles.marketplace.service.PostingService;
import com.realthomasmiles.marketplace.service.SaleService;
import com.realthomasmiles.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/sale")
@Validated
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private PostingService postingService;

    @Autowired
    private UserService userService;

    @GetMapping("/postingSale")
    public Response<Object> getSaleByPosting(@RequestParam(name = "posting") Long postingId) {
        Optional<PostingDto> postingDto = Optional.ofNullable(postingService.getPostingById(postingId));
        if (postingDto.isPresent()) {
            return Response
                    .ok()
                    .setPayload(saleService.getSaleByPosting(postingDto.get()));
        }

        return Response
                .ok()
                .setErrors("Unable to process request");
    }

    @GetMapping("/myPurchases")
    public Response<Object> getCurrentUsersPurchases() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        Optional<UserDto> userDto = Optional.ofNullable(userService.findUserByEmail(email));
        if (userDto.isPresent()) {
            return Response
                    .ok()
                    .setPayload(saleService.getSalesByBuyer(userDto.get()));
        }

        return Response
                .badRequest()
                .setErrors("Unable to process request");
    }

    @GetMapping("/mySales")
    public Response<Object> getCurrentUsersSales() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        Optional<UserDto> userDto = Optional.ofNullable(userService.findUserByEmail(email));
        if (userDto.isPresent()) {
            return Response
                    .ok()
                    .setPayload(saleService.getSalesBySeller(userDto.get()));
        }

        return Response
                .badRequest()
                .setErrors("Unable to process request");
    }

    @PostMapping("/makeSale")
    public Response<Object> makeSale(@RequestBody @Valid MakeSaleRequest makeSaleRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        Optional<UserDto> userDto = Optional.ofNullable(userService.findUserByEmail(email));
        if (userDto.isPresent()) {
            Optional<PostingDto> postingDto = Optional.ofNullable(postingService
                    .getPostingById(makeSaleRequest.getPostingId()));
            if (postingDto.isPresent()) {
                return Response
                        .ok()
                        .setPayload(saleService.makeSale(makeSaleRequest, userDto.get()));
            }
        }

        return Response
                .badRequest()
                .setErrors("Unable to process request");
    }

}
