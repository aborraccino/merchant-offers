package com.merchant.store.offers.repository;

import com.merchant.store.offers.dto.CurrencyEnumDto;
import com.merchant.store.offers.dto.OfferDto;
import com.merchant.store.offers.dto.OfferResponseDto;
import com.merchant.store.offers.dto.OffersDetailCreateRequestDto;
import com.merchant.store.offers.model.CurrencyEnum;
import com.merchant.store.offers.model.Offer;
import com.merchant.store.offers.model.OffersDetail;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public final class DummyFactory {

    public static Offer givenDummyValidOfferNoDetails() {
        Offer offer = new Offer();
        offer.setCurrency(CurrencyEnum.EUR);
        offer.setDescription("offer description");
        offer.setOfferCode("offer code");
        offer.setPrice(100.5);
        LocalDateTime now = LocalDateTime.now();
        offer.setOfferStartDate(now);
        return offer;
    }

    public static Offer givenDummyOfferValidWithOneDetail() {
        OffersDetail offersDetail = new OffersDetail();
        offersDetail.setOfferDetailCode("offer detail code");
        offersDetail.setOfferDetailDescription("offer detail description");
        offersDetail.setQuantity(10);
        Offer offer = DummyFactory.givenDummyValidOfferNoDetails();
        offersDetail.setOffer(offer);
        offer.setOffersDetailList(Collections.singletonList(offersDetail));
        return offer;
    }

    public static Offer givenDummyInvalidOfferValidWithoutCurrency() {
        Offer offer = new Offer();
        offer.setCurrency(null);
        LocalDateTime now = LocalDateTime.now();
        offer.setOfferStartDate(now);
        offer.setDescription("a special offer!");
        offer.setOfferCode("12345");
        offer.setPrice(10.5);
        return offer;
    }

    public static Offer givenDummyInvalidOfferValidWithoutPrice() {
        Offer offer = new Offer();
        offer.setCurrency(CurrencyEnum.EUR);
        LocalDateTime now = LocalDateTime.now();
        offer.setOfferStartDate(now);
        offer.setDescription("a special offer!");
        offer.setOfferCode("12345");
        offer.setPrice(null);
        return offer;
    }

    public static Offer givenDummyInvalidOfferValidWithoutCode() {
        Offer offer = new Offer();
        offer.setCurrency(CurrencyEnum.EUR);
        LocalDateTime now = LocalDateTime.now();
        offer.setOfferStartDate(now);
        offer.setDescription("a special offer!");
        offer.setOfferCode(null);
        offer.setPrice(100.5);
        return offer;
    }

    public static Offer givenDummyInvalidOfferValidWithoutStartDate() {
        Offer offer = new Offer();
        offer.setCurrency(null);
        offer.setDescription("a special offer!");
        offer.setOfferCode("12345");
        offer.setPrice(10.5);
        return offer;
    }

    public static Offer givenDummyValidOfferNoExpirationNorDetails() {
        Offer offer = new Offer();
        offer.setCurrency(CurrencyEnum.USD);
        offer.setDescription("a special offer!");
        offer.setOfferCode("12345");
        offer.setPrice(10.5);
        offer.setOfferStartDate(LocalDateTime.now());
        return offer;
    }

    public static Offer givenDummyValidOfferWithExpirationWithoutDetails(LocalDateTime startDate, LocalDateTime expireDate) {
        Offer offer = new Offer();
        offer.setOfferId(UUID.randomUUID());
        offer.setCurrency(CurrencyEnum.EUR);
        offer.setDescription("offer description");
        offer.setOfferCode("offer code");
        offer.setPrice(100.5);
        offer.setOfferStartDate(startDate);
        offer.setOfferExpireDate(expireDate);
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

    public static OfferDto givenDummyValidOfferDtoNoDetails() {
        OfferDto offerCreateRequestDto = new OfferDto();
        offerCreateRequestDto.setCurrency(CurrencyEnumDto.EUR);
        offerCreateRequestDto.setExpirationDelay(60);
        offerCreateRequestDto.setDescription("offer description");
        offerCreateRequestDto.setOfferCode("offer code");
        offerCreateRequestDto.setPrice(100.5);
        return offerCreateRequestDto;
    }

    public static OfferDto givenDummyValidOfferDtoNoExpirationNorDetails() {
        OfferDto offerCreateRequestDto = new OfferDto();
        offerCreateRequestDto.setCurrency(CurrencyEnumDto.EUR);
        offerCreateRequestDto.setDescription("offer description");
        offerCreateRequestDto.setOfferCode("offer code");
        offerCreateRequestDto.setPrice(100.5);
        return offerCreateRequestDto;
    }

    public static OfferDto givenDummyValidOfferDtoWithOneDetail() {
        OffersDetailCreateRequestDto dummyOffersDetailCreateRequestDto = new OffersDetailCreateRequestDto();
        dummyOffersDetailCreateRequestDto.setOfferDetailCode("detail code");
        dummyOffersDetailCreateRequestDto.setOfferDetailDescription("description");
        dummyOffersDetailCreateRequestDto.setQuantity(1);
        OfferDto offerCreateRequestDto = givenDummyValidOfferDtoNoDetails();
        offerCreateRequestDto.setOffersDetail(List.of(dummyOffersDetailCreateRequestDto));

        return offerCreateRequestDto;
    }

    public static OfferDto givenDummyValidOfferDtoWithExpirationWithoutDetails() {
        OfferDto offerCreateRequestDto = new OfferDto();
        offerCreateRequestDto.setCurrency(CurrencyEnumDto.EUR);
        offerCreateRequestDto.setDescription("offer description");
        offerCreateRequestDto.setOfferCode("offer code");
        offerCreateRequestDto.setPrice(100.5);
        offerCreateRequestDto.setExpirationDelay(10);
        return offerCreateRequestDto;
    }

    public static OfferResponseDto givenDummyValidOfferResponseDtoNoDetails(LocalDateTime startDate) {
        OfferResponseDto offerResponseDto = new OfferResponseDto(UUID.randomUUID());
        offerResponseDto.setCurrency(CurrencyEnumDto.EUR);
        offerResponseDto.setExpired(false);
        offerResponseDto.setOfferStartDate(startDate);
        offerResponseDto.setDescription("offer description");
        offerResponseDto.setOfferCode("offer code");
        offerResponseDto.setPrice(100.5);
        return offerResponseDto;
    }

    public static OfferResponseDto givenDummyValidOfferResponseDto(LocalDateTime startDate) {
        OffersDetailCreateRequestDto dummyOffersDetailCreateRequestDto = new OffersDetailCreateRequestDto();
        dummyOffersDetailCreateRequestDto.setOfferDetailCode("detail code");
        dummyOffersDetailCreateRequestDto.setOfferDetailDescription("description");
        dummyOffersDetailCreateRequestDto.setQuantity(1);
        OfferResponseDto offerResponseDto = givenDummyValidOfferResponseDtoNoDetails(startDate);
        offerResponseDto.setOffersDetail(List.of(dummyOffersDetailCreateRequestDto));

        return offerResponseDto;
    }
}
