package com.realthomasmiles.marketplace.dto.model.marketplace;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostingDto {

    private Long id;
    private String article;
    private Boolean isActive;
    private Long categoryId;
    private String category;
    private Long authorId;
    private String author;
    private Long locationId;
    private String location;
    private String posted;
    private String name;
    private String description;
    private Long price;

}
