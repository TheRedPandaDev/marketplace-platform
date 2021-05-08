package com.realthomasmiles.marketplace.service;

import com.realthomasmiles.marketplace.dto.model.user.UserDto;

public interface UserService {

    UserDto signUp(UserDto userDto);

    UserDto findUserByEmail(String email);

    UserDto updateProfile(UserDto userDto);

    UserDto changePassword(UserDto userDto, String newPassword);

}
