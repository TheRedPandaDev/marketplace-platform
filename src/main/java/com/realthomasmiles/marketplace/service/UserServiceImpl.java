package com.realthomasmiles.marketplace.service;

import com.realthomasmiles.marketplace.dto.mapper.UserMapper;
import com.realthomasmiles.marketplace.dto.model.user.UserDto;
import com.realthomasmiles.marketplace.exception.EntityType;
import com.realthomasmiles.marketplace.exception.ExceptionType;
import com.realthomasmiles.marketplace.exception.MarketPlaceException;
import com.realthomasmiles.marketplace.model.user.Role;
import com.realthomasmiles.marketplace.model.user.User;
import com.realthomasmiles.marketplace.model.user.UserRole;
import com.realthomasmiles.marketplace.repository.user.RoleRepository;
import com.realthomasmiles.marketplace.repository.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto signUp(UserDto userDto) {
        Role userRole;
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user == null) {
            if (userDto.getIsAdmin()) {
                userRole = roleRepository.findByRole(UserRole.ADMIN);
            } else {
                userRole = roleRepository.findByRole(UserRole.USER);
            }

            user = new User()
                    .setEmail(userDto.getEmail())
                    .setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()))
                    .setRoles(new HashSet<>(Collections.singletonList(userRole)))
                    .setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName())
                    .setPhoneNumber(userDto.getPhoneNumber());

            return UserMapper.toUserDto(userRepository.save(user));
        }

        throw exception(EntityType.USER, ExceptionType.DUPLICATE_ENTITY, userDto.getEmail());
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return UserMapper.toUserDto(user.get());
        }

        throw exception(EntityType.POSTING, ExceptionType.ENTITY_NOT_FOUND, id.toString());
    }

    @Override
    public UserDto findUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isPresent()) {
            return UserMapper.toUserDto(user.get());
        }

        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, email);
    }

    @Override
    public UserDto updateProfile(UserDto userDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            User userModel = user.get();
            userModel.setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName())
                    .setPhoneNumber(userDto.getPhoneNumber());

            return UserMapper.toUserDto(userRepository.save(userModel));
        }

        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userDto.getEmail());
    }

    @Override
    public UserDto changePassword(UserDto userDto, String newPassword) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            User userModel = user.get();
            userModel.setPassword(bCryptPasswordEncoder.encode(newPassword));

            return UserMapper.toUserDto(userRepository.save(userModel));
        }

        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userDto.getEmail());
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return MarketPlaceException.throwException(entityType, exceptionType, args);
    }

}
