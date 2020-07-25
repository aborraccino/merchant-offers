package com.merchant.store.offers.service.impl;

import com.merchant.store.offers.dto.OfferDto;
import com.merchant.store.offers.dto.OfferResponseDto;
import com.merchant.store.offers.exception.DuplicateResourceException;
import com.merchant.store.offers.exception.ModelMappingException;
import com.merchant.store.offers.exception.OfferExpiredException;
import com.merchant.store.offers.exception.ResourceNotFoundException;
import com.merchant.store.offers.mapper.DtoToModelMapper;
import com.merchant.store.offers.mapper.ModelToDtoMapper;
import com.merchant.store.offers.model.Offer;
import com.merchant.store.offers.repository.OfferRepository;
import com.merchant.store.offers.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class OfferServiceImpl implements OfferService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfferServiceImpl.class);

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private DtoToModelMapper<OfferDto, Offer> offerCreateRequestDtoToOfferMapper;

    @Autowired
    private ModelToDtoMapper<Offer, OfferResponseDto> offerToOfferResponseDto;

    private Supplier<LocalDateTime> offerClock;

    @Autowired
    public OfferServiceImpl(Supplier<LocalDateTime> offerClock) {
        this.offerClock = offerClock;
    }

    @Override
    @Transactional
    public UUID createOffer(OfferDto offerCreateRequestDto) {
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
    @Transactional
    public OfferResponseDto updateOffer(OfferDto offerDto, String offerId) {
        LOGGER.info("updateOffer(offerId={})", offerId);

        getAndCheckIfOfferExistsOrIsExpired(offerId);

        Offer offer = offerCreateRequestDtoToOfferMapper.map(offerDto).orElseThrow(
                () -> new ModelMappingException("offerCreateRequestDtoToOfferMapper was unable to convert the model")
        );

        offer.setOfferId(UUID.fromString(offerId));
        final Offer offerUpdated = offerRepository.save(offer);
        return offerToOfferResponseDto.map(offerUpdated).orElseThrow(
                () -> new ModelMappingException("offerToOfferResponseDto was unable to convert the model")
        );
    }

    @Override
    public OfferResponseDto getOfferById(String offerId) {
        LOGGER.info("getOfferById(id={})}", offerId);

        final Offer offer = getAndCheckIfOfferExistsOrIsExpired(offerId);

        return offerToOfferResponseDto.map(offer).orElseThrow(
                () -> new ModelMappingException("offerToOfferResponseDto was unable to convert the model")
        );
    }

    @Override
    public void expireOfferById(String offerId) {
        LOGGER.info("expireOfferById(id={})}", offerId);

        Offer offer = getAndCheckIfOfferExistsOrIsExpired(offerId);
        offer.setOfferExpireDate(offerClock.get());

        offerRepository.save(offer);
    }

    private Offer getAndCheckIfOfferExistsOrIsExpired(String offerId) {
        Optional<Offer> offer = offerRepository.findById(UUID.fromString(offerId));

        if (offer.isEmpty()) {
            LOGGER.error("Offer not found with id {}", offerId);
            throw new ResourceNotFoundException("Offer not found");
        }

        if(Objects.nonNull(offer.get().getOfferExpireDate()) &&
                offerClock.get().isAfter(offer.get().getOfferExpireDate())) {
            LOGGER.error("Offer expired with id {}", offerId);
            throw new OfferExpiredException("Offer expired");
        }

        return offer.get();
    }
}
