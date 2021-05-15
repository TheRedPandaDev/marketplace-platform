package com.realthomasmiles.marketplace.service;

import com.realthomasmiles.marketplace.dto.model.marketplace.OfferDto;
import com.realthomasmiles.marketplace.dto.model.marketplace.PostingDto;

public interface EmailService {

    void notifyAboutSuccessfulSignUp(String userEmail, String userFullName);

    void notifyAboutPosting(String userEmail, PostingDto postingDto);

    void notifyAboutOffer(String userEmail, OfferDto offerDto);

}
