package com.merchant.store.offers.dto;

import java.util.UUID;

public class OfferResponseDto extends OfferCreateRequestDto {

    private UUID offerId;

    public OfferResponseDto(UUID offerId) {
        this.offerId = offerId;
    }

    public UUID getOfferId() {
        return offerId;
    }

    public void setOfferId(UUID offerId) {
        this.offerId = offerId;
    }
}
