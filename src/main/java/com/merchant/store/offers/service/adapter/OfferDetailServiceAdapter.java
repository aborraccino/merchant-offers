package com.merchant.store.offers.service.adapter;

import com.merchant.store.offers.dto.OfferDetailResponseDto;
import com.merchant.store.offers.dto.OffersDetailDto;

import java.util.UUID;

public interface OfferDetailServiceAdapter {

    UUID addOfferDetail(String offerId, OffersDetailDto offersDetailCreateDto);

    void updateOfferDetail(String offerId, String offerDetailId, OffersDetailDto offersDetailUpdateRequestDto);

    OfferDetailResponseDto getOfferDetail(String offerId, String offerDetailId);
}
