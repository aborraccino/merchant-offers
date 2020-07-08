package com.merchant.store.offers.dto;

import javax.validation.constraints.Min;

public class OffersDetailUpdateRequestDto {

    private String offerDetailCode;

    private String offerDetailDescription;

    @Min(1)
    private Integer quantity;

    public OffersDetailUpdateRequestDto(){}

    public OffersDetailUpdateRequestDto(String offerDetailCode,
                                        String offerDetailDescription,
                                        @Min(1) Integer quantity) {
        this.offerDetailCode = offerDetailCode;
        this.offerDetailDescription = offerDetailDescription;
        this.quantity = quantity;
    }

    public String getOfferDetailCode() {
        return offerDetailCode;
    }

    public OffersDetailUpdateRequestDto setOfferDetailCode(String offerDetailCode) {
        this.offerDetailCode = offerDetailCode;
        return this;
    }

    public String getOfferDetailDescription() {
        return offerDetailDescription;
    }

    public OffersDetailUpdateRequestDto setOfferDetailDescription(String offerDetailDescription) {
        this.offerDetailDescription = offerDetailDescription;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OffersDetailUpdateRequestDto setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
