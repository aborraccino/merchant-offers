package com.merchant.store.offers.service.impl;

import com.merchant.store.offers.dto.OfferCreateRequestDto;
import com.merchant.store.offers.dto.OfferUpdateRequestDto;
import com.merchant.store.offers.exception.DuplicateResourceException;
import com.merchant.store.offers.exception.ModelMappingException;
import com.merchant.store.offers.mapper.DtoToModelMapper;
import com.merchant.store.offers.model.Offer;
import com.merchant.store.offers.repository.OfferRepository;
import com.merchant.store.offers.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class OfferServiceImpl implements OfferService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfferServiceImpl.class);

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private DtoToModelMapper<OfferCreateRequestDto, Offer> offerCreateRequestDtoToOfferMapper;

    @Override
    @Transactional
    public UUID createOffer(OfferCreateRequestDto offerCreateRequestDto) {
        LOGGER.info("createOffer(id={})", offerCreateRequestDto.getOfferCode());

        Optional<Offer> duplicatedOffer = offerRepository.findByOfferCode(offerCreateRequestDto.getOfferCode());

        if (duplicatedOffer.isPresent()) {
            LOGGER.error("offer with code {} and id {} is already present", duplicatedOffer.get().getOfferCode()
            , duplicatedOffer.get().getOfferId());
            throw new DuplicateResourceException(offerCreateRequestDto.getOfferCode());
        }

        Offer offer =
               offerCreateRequestDtoToOfferMapper.map(offerCreateRequestDto).orElseThrow(
                       () -> new ModelMappingException("offerModelToDtoMapper was unable to convert the model"));

        final Offer offerCreated = offerRepository.save(offer);

        LOGGER.info("new offer created = {}", offerCreated);
        return offerCreated.getOfferId();
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
