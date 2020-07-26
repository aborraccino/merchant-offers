package com.merchant.store.offers.service;

import com.merchant.store.offers.dto.OfferDetailResponseDto;
import com.merchant.store.offers.dto.OffersDetailDto;

import java.util.UUID;

public interface OfferDetailService {

    UUID addOfferDetail(OffersDetailDto offersDetailCreateDto);

    void updateOfferDetail(String offerDetailId, OffersDetailDto offersDetailUpdateRequestDto);

    OfferDetailResponseDto getOfferDetail(String offerDetailId);
}
