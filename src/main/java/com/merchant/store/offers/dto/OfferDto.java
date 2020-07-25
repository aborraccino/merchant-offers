package com.merchant.store.offers.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class OfferDto {

    @NotBlank
    private String offerCode;

    private String description;

    @NotNull
    private Double price;

    @NotNull
    private CurrencyEnumDto currency;

    // in seconds
    @Min(0)
    private Integer expirationDelay;

    private List<OffersDetailCreateRequestDto> offersDetail;

    public String getOfferCode() {
        return offerCode;
    }

    public OfferDto setOfferCode(String offerCode) {
        this.offerCode = offerCode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public OfferDto setPrice(Double price) {
        this.price = price;
        return this;
    }

    public CurrencyEnumDto getCurrency() {
        return currency;
    }

    public OfferDto setCurrency(CurrencyEnumDto currency) {
        this.currency = currency;
        return this;
    }

    public Integer getExpirationDelay() {
        return expirationDelay;
    }

    public OfferDto setExpirationDelay(Integer expirationDelay) {
        this.expirationDelay = expirationDelay;
        return this;
    }

    public List<OffersDetailCreateRequestDto> getOffersDetail() {
        return offersDetail;
    }

    public OfferDto setOffersDetail(List<OffersDetailCreateRequestDto> offersDetail) {
        this.offersDetail = offersDetail;
        return this;
    }

    @Override
    public String toString() {
        return "OfferCreateRequestDto{" +
                "offerCode='" + offerCode + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", currency=" + currency +
                ", expirationDelay=" + expirationDelay +
                ", offersDetail=" + offersDetail +
                '}';
    }
}
