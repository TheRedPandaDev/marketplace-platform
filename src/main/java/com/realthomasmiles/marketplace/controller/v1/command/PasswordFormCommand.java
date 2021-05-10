package com.realthomasmiles.marketplace.controller.v1.command;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class PasswordFormCommand {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 5, max = 36)
    private String password;

}
