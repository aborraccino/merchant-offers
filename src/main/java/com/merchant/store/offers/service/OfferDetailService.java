package com.merchant.store.offers.service;

import com.merchant.store.offers.dto.OfferDetailResponseDto;
import com.merchant.store.offers.dto.OffersDetailCreateRequestDto;
import com.merchant.store.offers.dto.OffersDetailUpdateRequestDto;

public interface OfferDetailService {

    OfferDetailResponseDto addOfferDetail(String offerId, OffersDetailCreateRequestDto offersDetailCreateDto);

    void updateOfferDetail(OffersDetailUpdateRequestDto offersDetailUpdateRequestDto);

    OfferDetailResponseDto getOfferDetail(String offerId, String offerDetailId);

    void removeOfferDetail(String offerId, String offerDetailId);
}
