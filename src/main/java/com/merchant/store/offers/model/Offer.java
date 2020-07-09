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

    @Column(name = "EXPIRATION")
    private Integer expirationDelay;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OffersDetail> offersDetailList;

    public UUID getOfferId() {
        return offerId;
    }

    public void setOfferId(UUID offerId) {
        this.offerId = offerId;
    }

    public String getOfferCode() {
        return offerCode;
    }

    public void setOfferCode(String offerCode) {
        this.offerCode = offerCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public Integer getExpirationDelay() {
        return expirationDelay;
    }

    public void setExpirationDelay(Integer expirationDelay) {
        this.expirationDelay = expirationDelay;
    }

    public List<OffersDetail> getOffersDetailList() {
        return offersDetailList;
    }

    public void setOffersDetailList(List<OffersDetail> offersDetailList) {
        this.offersDetailList = offersDetailList;
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
                ", expirationDelay=" + expirationDelay +
                ", offersDetailList=" + offersDetailList +
                '}';
    }
}
