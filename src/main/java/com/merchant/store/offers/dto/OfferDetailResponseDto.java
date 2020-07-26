package com.merchant.store.offers.dto;

import java.util.UUID;

public class OfferDetailResponseDto {

    UUID offerDetailId;

    private String offerDetailCode;

    private String offerDetailDescription;

    private Integer quantity;

    public OfferDetailResponseDto(UUID offerDetailId) {
        this.offerDetailId = offerDetailId;
    }

    public UUID getOfferDetailId() {
        return offerDetailId;
    }

    public OfferDetailResponseDto setOfferDetailId(UUID offerDetailId) {
        this.offerDetailId = offerDetailId;
        return this;
    }

    public String getOfferDetailCode() {
        return offerDetailCode;
    }

    public OfferDetailResponseDto setOfferDetailCode(String offerDetailCode) {
        this.offerDetailCode = offerDetailCode;
        return this;
    }

    public String getOfferDetailDescription() {
        return offerDetailDescription;
    }

    public OfferDetailResponseDto setOfferDetailDescription(String offerDetailDescription) {
        this.offerDetailDescription = offerDetailDescription;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OfferDetailResponseDto setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
