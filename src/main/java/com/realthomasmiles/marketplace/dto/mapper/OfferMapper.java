package com.realthomasmiles.marketplace.dto.mapper;

import com.realthomasmiles.marketplace.dto.model.marketplace.OfferDto;
import com.realthomasmiles.marketplace.model.marketplace.Offer;
import com.realthomasmiles.marketplace.util.DateUtils;

public class OfferMapper {
    public static OfferDto toOfferDto(Offer offer) {
        return new OfferDto()
                .setId(offer.getId())
                .setAuthor(offer.getAuthor().getFullName())
                .setPosting(offer.getPosting().getName())
                .setOffered(DateUtils.formattedDate(offer.getOffered()))
                .setAmount(offer.getAmount());
    }
}
