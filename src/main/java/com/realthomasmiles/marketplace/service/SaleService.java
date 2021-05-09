package com.realthomasmiles.marketplace.service;

import com.realthomasmiles.marketplace.controller.v1.request.MakeSaleRequest;
import com.realthomasmiles.marketplace.dto.model.marketplace.PostingDto;
import com.realthomasmiles.marketplace.dto.model.marketplace.SaleDto;
import com.realthomasmiles.marketplace.dto.model.user.UserDto;

import java.util.List;

public interface SaleService {

    SaleDto getSaleByPosting(PostingDto postingDto);

    List<SaleDto> getSalesByBuyer(UserDto userDto);

    List<SaleDto> getSalesBySeller(UserDto userDto);

    SaleDto makeSale(MakeSaleRequest makeSaleRequest, UserDto userDto);

}
