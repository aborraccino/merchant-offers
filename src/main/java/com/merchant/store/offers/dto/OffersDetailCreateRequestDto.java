package com.merchant.store.offers.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OffersDetailCreateRequestDto {

    @NotNull
    private String offerDetailCode;

    @NotBlank
    private String offerDetailDescription;

    @Min(1)
    private Integer quantity;

    public OffersDetailCreateRequestDto() {
    }

    public OffersDetailCreateRequestDto(String code, String description, int quantity) {
        this.offerDetailCode = code;
        this.offerDetailDescription = description;
        this.quantity = quantity;
    }

    public String getOfferDetailCode() {
        return offerDetailCode;
    }

    public OffersDetailCreateRequestDto setOfferDetailCode(String offerDetailCode) {
        this.offerDetailCode = offerDetailCode;
        return this;
    }

    public String getOfferDetailDescription() {
        return offerDetailDescription;
    }

    public OffersDetailCreateRequestDto setOfferDetailDescription(String offerDetailDescription) {
        this.offerDetailDescription = offerDetailDescription;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OffersDetailCreateRequestDto setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
