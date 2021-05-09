package com.realthomasmiles.marketplace.service;

import com.realthomasmiles.marketplace.controller.v1.request.MakeOfferRequest;
import com.realthomasmiles.marketplace.dto.model.marketplace.OfferDto;
import com.realthomasmiles.marketplace.dto.model.marketplace.PostingDto;
import com.realthomasmiles.marketplace.dto.model.user.UserDto;

import java.util.List;

public interface OfferService {

    List<OfferDto> getOffersByPosting(PostingDto postingDto);

    List<OfferDto> getOffersByUser(UserDto userDto);

    List<OfferDto> getOffersByPostingAndUser(PostingDto postingDto, UserDto userDto);

    OfferDto makeOffer(MakeOfferRequest makeOfferRequest, UserDto userDto);

}
