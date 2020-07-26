package com.merchant.store.offers.service.adapter.impl;

import com.merchant.store.offers.dto.OfferDetailResponseDto;
import com.merchant.store.offers.dto.OffersDetailDto;
import com.merchant.store.offers.service.OfferDetailService;
import com.merchant.store.offers.service.OfferService;
import com.merchant.store.offers.service.adapter.OfferDetailServiceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OfferDetailServiceAdapterImpl implements OfferDetailServiceAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfferDetailServiceAdapterImpl.class);

    @Autowired
    private OfferDetailService offerDetailService;

    @Autowired
    private OfferService offerService;

    @Override
    @Transactional
    public UUID addOfferDetail(String offerId, OffersDetailDto offersDetailCreateDto) {
        LOGGER.info("addOfferDetail(offerId={}, offersDetailCreateDto={})", offerId, offersDetailCreateDto);

        checkIfOfferExists(offerId);
        offersDetailCreateDto.setOfferId(UUID.fromString(offerId));

        // if not, save new item
        return offerDetailService.addOfferDetail(offersDetailCreateDto);
    }

    @Override
    public void updateOfferDetail(String offerId,
                                  String offerDetailId,
                                  OffersDetailDto offersDetailUpdateRequestDto) {
        LOGGER.info("updateOfferDetail(offerId={}, offersDetailUpdateRequestDto={})", offerId, offersDetailUpdateRequestDto);

        checkIfOfferExists(offerId);
        offersDetailUpdateRequestDto.setOfferId(UUID.fromString(offerId));

        // if not, save new item
        offerDetailService.updateOfferDetail(offerDetailId, offersDetailUpdateRequestDto);
    }

    @Override
    public OfferDetailResponseDto getOfferDetail(String offerId, String offerDetailId) {
        LOGGER.info("getOfferDetail(offerId={}, offerDetailId={})", offerId, offerDetailId);

        checkIfOfferExists(offerId);

        // if not, save new item
        return offerDetailService.getOfferDetail(offerDetailId);
    }

    private void checkIfOfferExists(String offerId) {
        // throws exception if offer does not exist
        offerService.getOfferById(offerId);
        LOGGER.info("offer exists");
    }
}
