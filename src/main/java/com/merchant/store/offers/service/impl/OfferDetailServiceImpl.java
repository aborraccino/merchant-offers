package com.merchant.store.offers.service.impl;

import com.merchant.store.offers.dto.OfferDetailResponseDto;
import com.merchant.store.offers.dto.OffersDetailCreateRequestDto;
import com.merchant.store.offers.dto.OffersDetailUpdateRequestDto;
import com.merchant.store.offers.service.OfferDetailService;
import org.springframework.stereotype.Service;

@Service
public class OfferDetailServiceImpl implements OfferDetailService {

    @Override
    public OfferDetailResponseDto addOfferDetail(String offerId, OffersDetailCreateRequestDto offersDetailCreateDto) {
        return null;
    }

    @Override
    public void updateOfferDetail(OffersDetailUpdateRequestDto offersDetailUpdateRequestDto) {

    }

    @Override
    public OfferDetailResponseDto getOfferDetail(String offerId, String offerDetailId) {
        return null;
    }

    @Override
    public void removeOfferDetail(String offerId, String offerDetailId) {

    }
}
