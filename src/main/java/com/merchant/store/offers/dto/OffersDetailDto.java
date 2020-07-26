package com.merchant.store.offers.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class OffersDetailDto {

    @NotNull
    private String offerDetailCode;

    @NotBlank
    private String offerDetailDescription;

    @Min(1)
    private Integer quantity;

    @JsonIgnore
    private UUID offerId;

    public OffersDetailDto() {
    }

    public OffersDetailDto(String code, String description, int quantity) {
        this.offerDetailCode = code;
        this.offerDetailDescription = description;
        this.quantity = quantity;
    }

    public String getOfferDetailCode() {
        return offerDetailCode;
    }

    public OffersDetailDto setOfferDetailCode(String offerDetailCode) {
        this.offerDetailCode = offerDetailCode;
        return this;
    }

    public String getOfferDetailDescription() {
        return offerDetailDescription;
    }

    public OffersDetailDto setOfferDetailDescription(String offerDetailDescription) {
        this.offerDetailDescription = offerDetailDescription;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OffersDetailDto setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public UUID getOfferId() {
        return offerId;
    }

    public void setOfferId(UUID offerId) {
        this.offerId = offerId;
    }
}
