package com.realthomasmiles.marketplace.controller.v1.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostPostingRequest {

    @NotBlank
    private Long categoryId;
    @NotBlank
    private Long locationId;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private Long price;

}
