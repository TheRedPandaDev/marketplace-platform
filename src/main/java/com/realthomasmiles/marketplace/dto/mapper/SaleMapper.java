package com.realthomasmiles.marketplace.dto.mapper;

import com.realthomasmiles.marketplace.dto.model.marketplace.SaleDto;
import com.realthomasmiles.marketplace.model.marketplace.Sale;
import com.realthomasmiles.marketplace.util.DateUtils;

public class SaleMapper {
    public static SaleDto toSaleDto(Sale sale) {
        return new SaleDto()
                .setId(sale.getId())
                .setSeller(sale.getSeller().getFullName())
                .setPosting(sale.getPosting().getName())
                .setBuyer(sale.getBuyer().getFullName())
                .setMade(DateUtils.formattedDate(sale.getMade()))
                .setOffer(sale.getOffer().getAmount());
    }
}
