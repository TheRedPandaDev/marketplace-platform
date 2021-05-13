package com.realthomasmiles.marketplace.controller.v1.command;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class SignUpFormCommand {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 5, max = 36)
    private String password;

    @NotBlank
    @Size(min = 1, max = 40, message =
    "First name must be between 1 and 40 characters")
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 40, message =
    "Last name must be between 1 and 40 characters")
    private String lastName;

    @NotBlank
    @Size(min = 5, max = 13, message =
    "Phone number must be between 5 and 13 characters")
    private String phoneNumber;

}
