package com.merchant.store.offers.service.impl;

import com.merchant.store.offers.dto.OfferCreateRequestDto;
import com.merchant.store.offers.dto.OfferUpdateRequestDto;
import com.merchant.store.offers.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class OfferServiceImpl implements OfferService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfferServiceImpl.class);

    @Override
    public UUID createOffer(OfferCreateRequestDto offercCreateDto) {
        return null;
    }

    @Override
    public void updateOffer(OfferUpdateRequestDto offerUpdateDto) {

    }

    @Override
    public OfferCreateRequestDto getOfferById(long id) {
        return null;
    }

    @Override
    public void expireOfferById(long offerId) {

    }
}
