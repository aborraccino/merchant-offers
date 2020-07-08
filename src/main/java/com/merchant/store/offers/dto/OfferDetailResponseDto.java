package com.merchant.store.offers.dto;

import java.util.UUID;

public class OfferDetailResponseDto extends OffersDetailCreateRequestDto {

    UUID offerDetailId;

    UUID offerId;

    public OfferDetailResponseDto(UUID offerDetailId, UUID offerId) {
        this.offerDetailId = offerDetailId;
        this.offerId = offerId;
    }

    public UUID getOfferDetailId() {
        return offerDetailId;
    }

    public OfferDetailResponseDto setOfferDetailId(UUID offerDetailId) {
        this.offerDetailId = offerDetailId;
        return this;
    }

    public UUID getOfferId() {
        return offerId;
    }

    public OfferDetailResponseDto setOfferId(UUID offerId) {
        this.offerId = offerId;
        return this;
    }
}
