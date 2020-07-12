package com.merchant.store.offers.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(name="OFFER")
public class Offer {

    @Id
    @GeneratedValue
    @Column(name = "OFFER_ID")
    private UUID offerId;

    @Column(name = "OFFER_CODE", nullable=false, length=100, unique = true)
    private String offerCode;

    @Column(name = "OFFER_DESCRIPTION", length=300)
    private String description;

    @Column(name = "PRICE", nullable=false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY", nullable=false)
    private CurrencyEnum currency;

    @Column(name = "OFFER_START_DATE", nullable=false)
    private LocalDateTime offerStartDate;

    @Column(name = "OFFER_EXPIRE_DATE")
    private LocalDateTime offerExpireDate;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OffersDetail> offersDetailList = new ArrayList<>();


    public UUID getOfferId() {
        return offerId;
    }

    public Offer setOfferId(UUID offerId) {
        this.offerId = offerId;
        return this;
    }

    public String getOfferCode() {
        return offerCode;
    }

    public Offer setOfferCode(String offerCode) {
        this.offerCode = offerCode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Offer setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Offer setPrice(Double price) {
        this.price = price;
        return this;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public Offer setCurrency(CurrencyEnum currency) {
        this.currency = currency;
        return this;
    }

    public LocalDateTime getOfferStartDate() {
        return offerStartDate;
    }

    public Offer setOfferStartDate(LocalDateTime offerStartDate) {
        this.offerStartDate = offerStartDate;
        return this;
    }

    public LocalDateTime getOfferExpireDate() {
        return offerExpireDate;
    }

    public Offer setOfferExpireDate(LocalDateTime offerExpireDate) {
        this.offerExpireDate = offerExpireDate;
        return this;
    }

    public List<OffersDetail> getOffersDetailList() {
        return offersDetailList;
    }

    public Offer setOffersDetailList(List<OffersDetail> offersDetailList) {
        this.offersDetailList = offersDetailList;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Offer offer = (Offer) o;
        return Objects.equals(offerId, offer.offerId) &&
                Objects.equals(offerCode, offer.offerCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offerId, offerCode);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "offerId=" + offerId +
                ", offerCode='" + offerCode + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", currency=" + currency +
                ", offerStartDate=" + offerStartDate +
                ", offerExpireDate=" + offerExpireDate +
                ", offersDetailList=" + offersDetailList +
                '}';
    }
}
