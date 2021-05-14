package com.realthomasmiles.marketplace.dto.mapper;

import com.realthomasmiles.marketplace.dto.model.marketplace.PostingDto;
import com.realthomasmiles.marketplace.model.marketplace.Posting;
import com.realthomasmiles.marketplace.util.DateUtils;

public class PostingMapper {
    public static PostingDto toPostingDto(Posting posting) {
        return new PostingDto()
                .setId(posting.getId())
                .setPosted(DateUtils.formattedDate(posting.getPosted()))
                .setArticle(posting.getArticle())
                .setPhoto(posting.getPhoto())
                .setAuthorId(posting.getAuthor().getId())
                .setAuthor(posting.getAuthor().getFullName())
                .setDescription(posting.getDescription())
                .setCategoryId(posting.getCategory().getId())
                .setCategory(posting.getCategory().getName())
                .setIsActive(posting.getIsActive())
                .setLocationId(posting.getLocation().getId())
                .setLocation(posting.getLocation().getName())
                .setPrice(posting.getPrice())
                .setName(posting.getName());
    }
}
