package com.realthomasmiles.marketplace.controller.v1.api;

import com.realthomasmiles.marketplace.controller.v1.request.UserSignUpRequest;
import com.realthomasmiles.marketplace.dto.model.user.UserDto;
import com.realthomasmiles.marketplace.dto.response.Response;
import com.realthomasmiles.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signUp")
    public Response<Object> signUp(@RequestBody @Valid UserSignUpRequest userSignUpRequest) {
        return Response
                .ok()
                .setPayload(registerUser(userSignUpRequest, false));
    }

    private UserDto registerUser(UserSignUpRequest userSignUpRequest, Boolean isAdmin) {
        UserDto userDto = new UserDto()
                .setEmail(userSignUpRequest.getEmail())
                .setPassword(userSignUpRequest.getPassword())
                .setFirstName(userSignUpRequest.getFirstName())
                .setLastName(userSignUpRequest.getLastName())
                .setPhoneNumber(userSignUpRequest.getPhoneNumber())
                .setIsAdmin(isAdmin);

        return userService.signUp(userDto);
    }

}
