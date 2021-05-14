package com.realthomasmiles.marketplace.controller.v1.command;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class MakeOfferCommand {

    @NotNull(message = "")
    @Min(value = 1, message = "Price should not be less than 1")
    @Max(value = 10000000000L, message =
            "Price should not be greater than 10000000000")
    private Long amount;

}
