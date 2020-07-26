package com.merchant.store.offers.service;

import com.merchant.store.offers.dto.OfferDto;
import com.merchant.store.offers.dto.OfferResponseDto;

public interface OfferService {

    OfferResponseDto createOffer(OfferDto offerCreateDto);

    OfferResponseDto updateOffer(OfferDto offerUpdateDto, String offerId);

    OfferResponseDto getOfferById(String offerId);

    void expireOfferById(String offerId);
}
