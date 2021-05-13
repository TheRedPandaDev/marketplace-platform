package com.realthomasmiles.marketplace.controller.v1.ui;

import com.realthomasmiles.marketplace.controller.v1.command.PasswordFormCommand;
import com.realthomasmiles.marketplace.controller.v1.command.ProfileFormCommand;
import com.realthomasmiles.marketplace.dto.model.marketplace.CategoryDto;
import com.realthomasmiles.marketplace.dto.model.marketplace.LocationDto;
import com.realthomasmiles.marketplace.dto.model.marketplace.PostingDto;
import com.realthomasmiles.marketplace.dto.model.user.UserDto;
import com.realthomasmiles.marketplace.service.CategoryService;
import com.realthomasmiles.marketplace.service.LocationService;
import com.realthomasmiles.marketplace.service.PostingService;
import com.realthomasmiles.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
public class MarketplaceController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostingService postingService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LocationService locationService;

    @GetMapping("/postings/all")
    public ModelAndView allPostings(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("postings");
        UserDto userDto = userService.findUserByEmail(principal.getName());
        List<PostingDto> postings = postingService.getAllPostings();
        List<CategoryDto> categories = categoryService.getAllCategories();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("postings", postings);
        modelAndView.addObject("userName", userDto.getFullName());
        modelAndView.addObject("isAdmin", userDto.getIsAdmin());

        return modelAndView;
    }

    @GetMapping("/postings/mine")
    public ModelAndView myPostings(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("postings");
        UserDto userDto = userService.findUserByEmail(principal.getName());
        List<PostingDto> postings = postingService.getPostingsByUser(userDto);
        List<CategoryDto> categories = categoryService.getAllCategories();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("postings", postings);
        modelAndView.addObject("userName", userDto.getFullName());
        modelAndView.addObject("isAdmin", userDto.getIsAdmin());

        return modelAndView;
    }

    @GetMapping("/postings/{id}")
    public ModelAndView postingById(Principal principal, @PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("posting");
        UserDto userDto = userService.findUserByEmail(principal.getName());
        try {
            Long postingId = Long.parseLong(id);
            PostingDto posting = postingService.getPostingById(Long.parseLong(id));
            modelAndView.addObject("posting", posting);
        } catch (NumberFormatException numberFormatException) {
            modelAndView.addObject("posting", null);
        }
        List<CategoryDto> categories = categoryService.getAllCategories();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("userName", userDto.getFullName());
        modelAndView.addObject("isAdmin", userDto.getIsAdmin());

        return modelAndView;
    }

    @GetMapping("/postings/category/{id}")
    public ModelAndView postingByCategory(Principal principal, @PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("postings");
        UserDto userDto = userService.findUserByEmail(principal.getName());
        try {
            Long categoryId = Long.parseLong(id);
            CategoryDto categoryDto = postingService.getCategoryById(categoryId);
            List<PostingDto> postings = postingService.getPostingsByCategory(categoryDto);
            modelAndView.addObject("postings", postings);
        } catch (NumberFormatException numberFormatException) {
            modelAndView.addObject("postings", Collections.emptyList());
        }
        List<CategoryDto> categories = categoryService.getAllCategories();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("userName", userDto.getFullName());
        modelAndView.addObject("isAdmin", userDto.getIsAdmin());

        return modelAndView;
    }

    @GetMapping("/postings/location/{id}")
    public ModelAndView postingByLocation(Principal principal, @PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("postings");
        UserDto userDto = userService.findUserByEmail(principal.getName());
        try {
            Long locationId = Long.parseLong(id);
            LocationDto locationDto = postingService.getLocationById(locationId);
            List<PostingDto> postings = postingService.getPostingsByLocation(locationDto);
            modelAndView.addObject("postings", postings);
        } catch (NumberFormatException numberFormatException) {
            modelAndView.addObject("postings", Collections.emptyList());
        }
        List<CategoryDto> categories = categoryService.getAllCategories();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("userName", userDto.getFullName());
        modelAndView.addObject("isAdmin", userDto.getIsAdmin());

        return modelAndView;
    }

    @GetMapping("/postings/author/{id}")
    public ModelAndView postingByAuthor(Principal principal, @PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("postings");
        UserDto userDto = userService.findUserByEmail(principal.getName());
        try {
            Long authorId = Long.parseLong(id);
            UserDto authorDto = userService.getUserById(authorId);
            List<PostingDto> postings = postingService.getPostingsByUser(authorDto);
            modelAndView.addObject("postings", postings);
        } catch (NumberFormatException numberFormatException) {
            modelAndView.addObject("postings", Collections.emptyList());
        }
        List<CategoryDto> categories = categoryService.getAllCategories();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("userName", userDto.getFullName());
        modelAndView.addObject("isAdmin", userDto.getIsAdmin());

        return modelAndView;
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        UserDto userDto = userService.findUserByEmail(principal.getName());
        List<CategoryDto> categories = categoryService.getAllCategories();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("currentUser", userDto);
        modelAndView.addObject("userName", userDto.getFullName());
        modelAndView.addObject("isAdmin", userDto.getIsAdmin());

        return modelAndView;
    }

    @GetMapping("/myProfile")
    public ModelAndView getUserProfile(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("profile");
        UserDto userDto = userService.findUserByEmail(principal.getName());
        List<CategoryDto> categories = categoryService.getAllCategories();
        modelAndView.addObject("categories", categories);
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
        modelAndView.addObject("isAdmin", userDto.getIsAdmin());

        return modelAndView;
    }

    @PostMapping("/myProfile")
    public ModelAndView updateUserProfile(Principal principal,
                                          @Valid @ModelAttribute("profileForm") ProfileFormCommand profileFormCommand,
                                          BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("profile");
        UserDto userDto = userService.findUserByEmail(principal.getName());
        List<CategoryDto> categories = categoryService.getAllCategories();
        modelAndView.addObject("categories", categories);
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
            modelAndView.addObject("isAdmin", userDto.getIsAdmin());
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
            List<CategoryDto> categories = categoryService.getAllCategories();
            modelAndView.addObject("categories", categories);
            ProfileFormCommand profileFormCommand = new ProfileFormCommand()
                    .setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName())
                    .setPhoneNumber(userDto.getPhoneNumber());
            modelAndView.addObject("profileForm", profileFormCommand);
            modelAndView.addObject("userName", userDto.getFullName());
            modelAndView.addObject("isAdmin", userDto.getIsAdmin());

            return modelAndView;
        } else {
            userService.changePassword(userDto, passwordFormCommand.getPassword());

            return new ModelAndView("login");
        }
    }

}
