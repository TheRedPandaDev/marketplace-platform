package com.realthomasmiles.marketplace.controller.v1.command;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;

@Data
@Accessors(chain = true)
public class PostingFormCommand {

    @NotBlank
    @Size(min = 1, max = 50, message =
    "Name must be between 1 and 50 characters")
    private String name;

    @NotBlank
    @Size(min = 1, max = 250, message =
    "Description must be between 1 and 250 characters")
    private String description;

    @NotNull(message = "")
    @Min(value = 1, message = "Price should not be less than 1")
    @Max(value = 10000000000L, message =
    "Price should not be greater than 10000000000")
    private Long price;

    @NotNull(message = "Category must not be empty")
    private Long categoryId;

    @NotNull(message = "Location must not be empty")
    private Long locationId;


}
