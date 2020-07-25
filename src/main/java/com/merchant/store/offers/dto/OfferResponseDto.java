package com.merchant.store.offers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OfferResponseDto {

    private UUID offerId;

    private String offerCode;

    private String description;

    private Double price;

    private CurrencyEnumDto currency;

    private List<OffersDetailCreateRequestDto> offersDetail;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime offerStartDate;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime offerExpireDate;

    private boolean isExpired;

    public OfferResponseDto(UUID offerId) {
        this.offerId = offerId;
    }

    public UUID getOfferId() {
        return offerId;
    }

    public OfferResponseDto setOfferId(UUID offerId) {
        this.offerId = offerId;
        return this;
    }

    public String getOfferCode() {
        return offerCode;
    }

    public OfferResponseDto setOfferCode(String offerCode) {
        this.offerCode = offerCode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferResponseDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public OfferResponseDto setPrice(Double price) {
        this.price = price;
        return this;
    }

    public CurrencyEnumDto getCurrency() {
        return currency;
    }

    public OfferResponseDto setCurrency(CurrencyEnumDto currency) {
        this.currency = currency;
        return this;
    }

    public List<OffersDetailCreateRequestDto> getOffersDetail() {
        return offersDetail;
    }

    public OfferResponseDto setOffersDetail(List<OffersDetailCreateRequestDto> offersDetail) {
        this.offersDetail = offersDetail;
        return this;
    }

    public LocalDateTime getOfferStartDate() {
        return offerStartDate;
    }

    public OfferResponseDto setOfferStartDate(LocalDateTime offerStartDate) {
        this.offerStartDate = offerStartDate;
        return this;
    }

    public LocalDateTime getOfferExpireDate() {
        return offerExpireDate;
    }

    public OfferResponseDto setOfferExpireDate(LocalDateTime offerExpireDate) {
        this.offerExpireDate = offerExpireDate;
        return this;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public OfferResponseDto setExpired(boolean expired) {
        isExpired = expired;
        return this;
    }
}
