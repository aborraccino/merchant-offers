package com.merchant.store.offers.repository;

import com.merchant.store.offers.dto.*;
import com.merchant.store.offers.model.CurrencyEnum;
import com.merchant.store.offers.model.Offer;
import com.merchant.store.offers.model.OffersDetail;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public final class DummyFactory {

    public static Offer givenDummyValidOfferNoDetails(UUID id) {
        Offer offer = new Offer();
        offer.setOfferId(id);
        offer.setCurrency(CurrencyEnum.EUR);
        offer.setDescription("offer description");
        offer.setOfferCode("offer code");
        offer.setPrice(100.5);
        LocalDateTime now = LocalDateTime.now();
        offer.setOfferStartDate(now);
        return offer;
    }

    public static Offer givenDummyOfferValidWithOneDetail(UUID offerId, UUID offerDetailId) {
        OffersDetail offersDetail = new OffersDetail();
        offersDetail.setOfferDetailId(offerId);
        offersDetail.setOfferDetailCode("offer detail code");
        offersDetail.setOfferDetailDescription("offer detail description");
        offersDetail.setQuantity(10);
        Offer offer = DummyFactory.givenDummyValidOfferNoDetails(offerDetailId);
        offersDetail.setOffer(offer);
        offer.setOffersDetailList(Collections.singletonList(offersDetail));
        return offer;
    }

    public static Offer givenDummyInvalidOfferValidWithoutCurrency() {
        Offer offer = new Offer();
        offer.setOfferId(UUID.randomUUID());
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
        offer.setOfferId(UUID.randomUUID());
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
        offer.setOfferId(UUID.randomUUID());
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
        offer.setOfferId(UUID.randomUUID());
        offer.setCurrency(null);
        offer.setDescription("a special offer!");
        offer.setOfferCode("12345");
        offer.setPrice(10.5);
        return offer;
    }

    public static Offer givenDummyValidOfferNoExpirationNorDetails() {
        Offer offer = new Offer();
        offer.setOfferId(UUID.randomUUID());
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

    public static OffersDetail givenDummyOfferDetail(Offer dummyOffer, UUID id) {
        OffersDetail offersDetail = new OffersDetail();
        offersDetail.setOfferDetailId(id);
        offersDetail.setOffer(dummyOffer);
        offersDetail.setQuantity(5);
        offersDetail.setOfferDetailDescription("description");
        offersDetail.setOfferDetailCode("offer detail code");
        return offersDetail;
    }

    public static OffersDetail givenDummyOfferDetailNoMainOffer() {
        OffersDetail offersDetail = new OffersDetail();
        offersDetail.setOfferDetailId(UUID.randomUUID());
        offersDetail.setQuantity(5);
        offersDetail.setOfferDetailDescription("description");
        offersDetail.setOfferDetailCode("offer detail code");
        return offersDetail;
    }

    public static OffersDetail givenDummyOfferDetailWithoutCode(Offer dummyOffer) {
        OffersDetail offersDetail = new OffersDetail();
        offersDetail.setOfferDetailId(UUID.randomUUID());
        offersDetail.setOffer(dummyOffer);
        offersDetail.setQuantity(5);
        offersDetail.setOfferDetailDescription("description");
        offersDetail.setOfferDetailCode(null);
        return offersDetail;
    }

    public static OffersDetail givenDummyValidOfferDetailNoQuantity(Offer dummyOffer) {
        OffersDetail offersDetail = new OffersDetail();
        offersDetail.setOfferDetailId(UUID.randomUUID());
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
        OffersDetailDto dummyOffersDetailCreateRequestDto = new OffersDetailDto();
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
        OffersDetailDto dummyOffersDetailCreateRequestDto = new OffersDetailDto();
        dummyOffersDetailCreateRequestDto.setOfferDetailCode("detail code");
        dummyOffersDetailCreateRequestDto.setOfferDetailDescription("description");
        dummyOffersDetailCreateRequestDto.setQuantity(1);
        OfferResponseDto offerResponseDto = givenDummyValidOfferResponseDtoNoDetails(startDate);
        offerResponseDto.setOffersDetail(List.of(dummyOffersDetailCreateRequestDto));

        return offerResponseDto;
    }

    public static OffersDetailDto givenDummyOfferDetailDto() {
        return new OffersDetailDto()
                .setOfferDetailCode("offer detail code")
                .setOfferDetailDescription("offer detail description")
                .setQuantity(10);
    }

    public static OffersDetailDto givenDummyInvalidQuantityOfferDetailDto() {
        return new OffersDetailDto()
                .setOfferDetailCode("offer detail code")
                .setOfferDetailDescription("offer detail description")
                .setQuantity(0);
    }

    public static OffersDetailDto givenDummyInvalidCodeOfferDetailDto() {
        return new OffersDetailDto()
                .setOfferDetailCode(null)
                .setOfferDetailDescription("offer detail description")
                .setQuantity(10);
    }

    public static OffersDetailDto givenDummyInvalidDescriptionOfferDetailDto() {
        return new OffersDetailDto()
                .setOfferDetailCode("offer detail code")
                .setOfferDetailDescription("")
                .setQuantity(10);
    }

    public static OfferDetailResponseDto givenDummyOfferDetailResponseDto() {
        return new OfferDetailResponseDto(UUID.randomUUID())
                .setOfferDetailCode("offer detail code")
                .setQuantity(10)
                .setOfferDetailDescription("offer detail description");
    }
}
