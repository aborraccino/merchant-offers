package com.merchant.store.offers.dto;

public class OfferUpdateRequestDto {

    private String offerCode;

    private String description;

    private Double price;

    private CurrencyEnumDto currency;

    private Integer expirationDelay;

    public String getOfferCode() {
        return offerCode;
    }

    public OfferUpdateRequestDto setOfferCode(String offerCode) {
        this.offerCode = offerCode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferUpdateRequestDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public OfferUpdateRequestDto setPrice(Double price) {
        this.price = price;
        return this;
    }

    public CurrencyEnumDto getCurrency() {
        return currency;
    }

    public OfferUpdateRequestDto setCurrency(CurrencyEnumDto currency) {
        this.currency = currency;
        return this;
    }

    public Integer getExpirationDelay() {
        return expirationDelay;
    }

    public OfferUpdateRequestDto setExpirationDelay(Integer expirationDelay) {
        this.expirationDelay = expirationDelay;
        return this;
    }
}
