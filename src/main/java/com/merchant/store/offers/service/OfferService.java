package com.merchant.store.offers.service;

import com.merchant.store.offers.dto.OfferUpdateRequestDto;
import com.merchant.store.offers.dto.OfferCreateRequestDto;

import java.util.UUID;

public interface OfferService {

    UUID createOffer(OfferCreateRequestDto offercCreateDto);

    void upateOffer(OfferUpdateRequestDto offerUpdateDto);

    OfferCreateRequestDto getOfferById(long id);

    void expireOfferById(long offerId);
}
