package com.realthomasmiles.marketplace.controller.v1.command;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class SignUpFormCommand {

    @NotBlank(message = "")
    @Email
    private String email;

    @NotBlank(message = "")
    @Size(min = 5, max = 36)
    private String password;

    @NotBlank(message = "Must not be blank")
    private String confirmPassword;

    @NotBlank(message = "")
    @Size(min = 1, max = 40, message =
    "First name must be between 1 and 40 characters")
    private String firstName;

    @NotBlank(message = "")
    @Size(min = 1, max = 40, message =
    "Last name must be between 1 and 40 characters")
    private String lastName;

    @NotBlank(message = "")
    @Pattern(regexp = "^[+]*7[0-9]{10}$", message =
    "Phone number must be in the format of +7**********")
    private String phoneNumber;

}
