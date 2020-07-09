package com.merchant.store.offers.repository;

import com.merchant.store.offers.model.CurrencyEnum;
import com.merchant.store.offers.model.Offer;
import com.merchant.store.offers.model.OffersDetail;

import java.util.Collections;

public final class DummyFactory {

    public static Offer givenDummyValidOfferNoDetails() {
        Offer offer = new Offer();
        offer.setCurrency(CurrencyEnum.EUR);
        offer.setExpirationDelay(60);
        offer.setDescription("offer description");
        offer.setOfferCode("offer code");
        offer.setPrice(100.5);
        return offer;
    }

    public static Offer givenDummyOfferValidWithOneDetail() {
        OffersDetail offersDetail = new OffersDetail();
        offersDetail.setOfferDetailCode("offer detail code");
        offersDetail.setOfferDetailDescription("offer detail description");
        offersDetail.setQuantity(10);
        Offer offer = DummyFactory.givenDummyValidOfferNoDetails();
        offer.setOffersDetailList(Collections.singletonList(offersDetail));
        return offer;
    }

    public static Offer givenDummyInvalidOfferValidWithoutCurrency() {
        Offer offer = new Offer();
        offer.setCurrency(null);
        offer.setExpirationDelay(60);
        offer.setDescription("a special offer!");
        offer.setOfferCode("12345");
        offer.setPrice(10.5);
        return offer;
    }

    public static Offer givenDummyInvalidOfferValidWithoutPrice() {
        Offer offer = new Offer();
        offer.setCurrency(CurrencyEnum.EUR);
        offer.setExpirationDelay(60);
        offer.setDescription("a special offer!");
        offer.setOfferCode("12345");
        offer.setPrice(null);
        return offer;
    }

    public static Offer givenDummyInvalidOfferValidWithoutCode() {
        Offer offer = new Offer();
        offer.setCurrency(CurrencyEnum.EUR);
        offer.setExpirationDelay(60);
        offer.setDescription("a special offer!");
        offer.setOfferCode(null);
        offer.setPrice(100.5);
        return offer;
    }

    public static OffersDetail givenDummyOfferDetail(Offer dummyOffer) {
        OffersDetail offersDetail = new OffersDetail();
        offersDetail.setOffer(dummyOffer);
        offersDetail.setQuantity(5);
        offersDetail.setOfferDetailDescription("description");
        offersDetail.setOfferDetailCode("offer detail code");
        return offersDetail;
    }

    public static OffersDetail givenDummyOfferDetailNoMainOffer() {
        OffersDetail offersDetail = new OffersDetail();
        offersDetail.setQuantity(5);
        offersDetail.setOfferDetailDescription("description");
        offersDetail.setOfferDetailCode("offer detail code");
        return offersDetail;
    }

    public static OffersDetail givenDummyOfferDetailWithoutCode(Offer dummyOffer) {
        OffersDetail offersDetail = new OffersDetail();
        offersDetail.setOffer(dummyOffer);
        offersDetail.setQuantity(5);
        offersDetail.setOfferDetailDescription("description");
        offersDetail.setOfferDetailCode(null);
        return offersDetail;
    }

    public static OffersDetail givenDummyValidOfferDetailNoQuantity(Offer dummyOffer) {
        OffersDetail offersDetail = new OffersDetail();
        offersDetail.setOffer(dummyOffer);
        offersDetail.setQuantity(null);
        offersDetail.setOfferDetailDescription("description");
        offersDetail.setOfferDetailCode("detail code");
        return offersDetail;
    }
}
