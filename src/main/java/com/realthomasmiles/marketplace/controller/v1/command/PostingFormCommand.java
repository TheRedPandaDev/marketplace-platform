package com.realthomasmiles.marketplace.controller.v1.command;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

    @NotBlank
    @Min(value = 1, message = "Price should not be less than 1")
    @Max(value = 1, message = "Price should not be greater than 150")
    private Long price;

    @NotBlank(message = "Category must not be empty")
    private Long categoryId;

    @NotBlank(message = "Location must not be empty")
    private Long locationId;


}
