package com.merchant.store.offers.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;
import java.util.UUID;

@Entity(name="OFFER_DETAIL")
public class OffersDetail {

    @Id
    @GeneratedValue
    @Column(name = "OFFER_DETAIL_ID")
    private UUID offerDetailId;

    @Column(name = "OFFER_DETAIL_CODE", nullable=false, length=100, unique = true)
    private String offerDetailCode;

    @Column(name = "OFFER_DESCRIPTION", length=300)
    private String offerDetailDescription;

    @Column(name = "QUANTITY", nullable=false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OFFER_ID", nullable=false)
    private Offer offer;

    public UUID getOfferDetailId() {
        return offerDetailId;
    }

    public void setOfferDetailId(UUID offerDetailId) {
        this.offerDetailId = offerDetailId;
    }

    public String getOfferDetailCode() {
        return offerDetailCode;
    }

    public void setOfferDetailCode(String offerDetailCode) {
        this.offerDetailCode = offerDetailCode;
    }

    public String getOfferDetailDescription() {
        return offerDetailDescription;
    }

    public void setOfferDetailDescription(String offerDetailDescription) {
        this.offerDetailDescription = offerDetailDescription;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OffersDetail that = (OffersDetail) o;
        return Objects.equals(offerDetailId, that.offerDetailId) &&
                Objects.equals(offerDetailCode, that.offerDetailCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offerDetailId, offerDetailCode);
    }

    @Override
    public String toString() {
        return "OffersDetail{" +
                "offerId=" + offerDetailId +
                ", offerDetailCode='" + offerDetailCode + '\'' +
                ", offerDetailDescription='" + offerDetailDescription + '\'' +
                ", quantity=" + quantity +
                ", offer=" + offer +
                '}';
    }
}
