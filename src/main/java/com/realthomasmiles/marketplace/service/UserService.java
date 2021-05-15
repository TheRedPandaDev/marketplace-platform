package com.realthomasmiles.marketplace.service;

import com.realthomasmiles.marketplace.dto.model.user.UserDto;

public interface UserService {

    Long getNumberOfUsers();

    UserDto signUp(UserDto userDto);

    UserDto getUserById(Long id);

    UserDto findUserByEmail(String email);

    UserDto updateProfile(UserDto userDto);

    UserDto changePassword(UserDto userDto, String newPassword);

}
