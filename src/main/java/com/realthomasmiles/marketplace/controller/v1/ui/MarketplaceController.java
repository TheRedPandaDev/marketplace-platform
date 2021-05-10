package com.realthomasmiles.marketplace.controller.v1.ui;

import com.realthomasmiles.marketplace.controller.v1.command.PasswordFormCommand;
import com.realthomasmiles.marketplace.controller.v1.command.ProfileFormCommand;
import com.realthomasmiles.marketplace.dto.model.user.UserDto;
import com.realthomasmiles.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@Validated
public class MarketplaceController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/dashboard")
    public ModelAndView dashboard(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        UserDto userDto = userService.findUserByEmail(principal.getName());
        modelAndView.addObject("currentUser", userDto);
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }

    @GetMapping("/myProfile")
    public ModelAndView getUserProfile(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("profile");
        UserDto userDto = userService.findUserByEmail(principal.getName());
        ProfileFormCommand profileFormCommand = new ProfileFormCommand()
                .setFirstName(userDto.getFirstName())
                .setLastName(userDto.getLastName())
                .setPhoneNumber(userDto.getPhoneNumber());
        PasswordFormCommand passwordFormCommand = new PasswordFormCommand()
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword());
        modelAndView.addObject("profileForm", profileFormCommand);
        modelAndView.addObject("passwordForm", passwordFormCommand);
        modelAndView.addObject("userName", userDto.getFullName());

        return modelAndView;
    }

    @PostMapping("/myProfile")
    public ModelAndView updateUserProfile(Principal principal,
                                          @Valid @ModelAttribute("profileForm") ProfileFormCommand profileFormCommand,
                                          BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("profile");
        UserDto userDto = userService.findUserByEmail(principal.getName());
        PasswordFormCommand passwordFormCommand = new PasswordFormCommand()
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword());
        modelAndView.addObject("passwordForm", passwordFormCommand);
        modelAndView.addObject("userName", userDto.getFullName());
        if (!bindingResult.hasErrors()) {
            userDto.setFirstName(profileFormCommand.getFirstName())
                    .setLastName(profileFormCommand.getLastName())
                    .setPhoneNumber(profileFormCommand.getPhoneNumber());
            userService.updateProfile(userDto);
            modelAndView.addObject("userName", userDto.getFullName());
        }

        return modelAndView;
    }

    @PostMapping("/password")
    public ModelAndView changePassword(Principal principal,
                                       @Valid @ModelAttribute("passwordForm") PasswordFormCommand passwordFormCommand,
                                       BindingResult bindingResult) {
        UserDto userDto = userService.findUserByEmail(principal.getName());
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("profile");
            ProfileFormCommand profileFormCommand = new ProfileFormCommand()
                    .setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName())
                    .setPhoneNumber(userDto.getPhoneNumber());
            modelAndView.addObject("profileForm", profileFormCommand);
            modelAndView.addObject("userName", userDto.getFullName());

            return modelAndView;
        } else {
            userService.changePassword(userDto, passwordFormCommand.getPassword());

            return new ModelAndView("login");
        }
    }

}
