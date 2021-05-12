package com.realthomasmiles.marketplace.controller.v1.ui;

import com.realthomasmiles.marketplace.controller.v1.command.SignUpFormCommand;
import com.realthomasmiles.marketplace.dto.model.user.UserDto;
import com.realthomasmiles.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Principal principal) {
        return principal != null ? "redirect:home" : "redirect:login";
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/home")
    public String home() {
        return "redirect:postings/all";
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);

        return "redirect:login";
    }

    @GetMapping("/signup")
    public ModelAndView signUp() {
        ModelAndView modelAndView = new ModelAndView("signup");
        modelAndView.addObject("signUpFormData", new SignUpFormCommand());

        return modelAndView;
    }

    @PostMapping("/signup")
    public ModelAndView postSignUp(@Valid @ModelAttribute("signUpFormData") SignUpFormCommand signUpFormCommand,
                                   BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("signup");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        } else {
            try {
                UserDto newUser = registerUser(signUpFormCommand);
            } catch (Exception exception) {
                bindingResult.rejectValue("email", "error.signUpFormCommand", exception.getMessage());

                return modelAndView;
            }
        }

        return new ModelAndView("login");
    }

    private UserDto registerUser(@Valid SignUpFormCommand signUpFormCommand) {
        UserDto userDto = new UserDto()
                .setEmail(signUpFormCommand.getEmail())
                .setPassword(signUpFormCommand.getPassword())
                .setFirstName(signUpFormCommand.getFirstName())
                .setLastName(signUpFormCommand.getLastName())
                .setPhoneNumber(signUpFormCommand.getPhoneNumber())
                .setIsAdmin(false);

        return userService.signUp(userDto);
    }

}
