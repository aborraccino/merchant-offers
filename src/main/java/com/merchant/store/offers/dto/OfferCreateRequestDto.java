package com.merchant.store.offers.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OfferCreateRequestDto {

    @NotNull
    private String offerCode;

    private String description;

    @NotNull
    private Double price;

    @NotNull
    private CurrencyEnumDto currency;

    private Integer expirationDelay;

    private List<OffersDetailCreateRequestDto> offersDetail;

    public String getOfferCode() {
        return offerCode;
    }

    public OfferCreateRequestDto setOfferCode(String offerCode) {
        this.offerCode = offerCode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferCreateRequestDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public OfferCreateRequestDto setPrice(Double price) {
        this.price = price;
        return this;
    }

    public CurrencyEnumDto getCurrency() {
        return currency;
    }

    public OfferCreateRequestDto setCurrency(CurrencyEnumDto currency) {
        this.currency = currency;
        return this;
    }

    public Integer getExpirationDelay() {
        return expirationDelay;
    }

    public OfferCreateRequestDto setExpirationDelay(Integer expirationDelay) {
        this.expirationDelay = expirationDelay;
        return this;
    }

    public List<OffersDetailCreateRequestDto> getOffersDetail() {
        return offersDetail;
    }

    public OfferCreateRequestDto setOffersDetail(List<OffersDetailCreateRequestDto> offersDetail) {
        this.offersDetail = offersDetail;
        return this;
    }
}
