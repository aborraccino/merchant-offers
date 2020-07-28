package com.merchant.store.offers.service.impl;

import com.merchant.store.offers.dto.OfferDto;
import com.merchant.store.offers.dto.OfferResponseDto;
import com.merchant.store.offers.exception.DuplicateResourceException;
import com.merchant.store.offers.exception.ModelMappingException;
import com.merchant.store.offers.exception.OfferExpiredException;
import com.merchant.store.offers.exception.ResourceNotFoundException;
import com.merchant.store.offers.mapper.DtoToModelMapper;
import com.merchant.store.offers.mapper.ModelToDtoMapper;
import com.merchant.store.offers.mapper.OfferDtoToOfferMapper;
import com.merchant.store.offers.model.Offer;
import com.merchant.store.offers.repository.DummyFactory;
import com.merchant.store.offers.repository.OfferRepository;
import com.merchant.store.offers.service.OfferService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OfferServiceImplTest {

    private static final LocalDateTime dummyLocalDateTime = LocalDateTime.of(2020, Month.JULY, 15, 10, 0, 0);
    private static final UUID OFFER_ID = UUID.fromString("3db46740-71d3-4881-8a0e-7206f9e1c089");

    private Supplier<LocalDateTime> offerClock = () -> dummyLocalDateTime;

    @InjectMocks
    private OfferService offerService = new OfferServiceImpl(offerClock);

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private DtoToModelMapper<OfferDto, Offer> offerDtoToOfferMapper = new OfferDtoToOfferMapper(offerClock);

    @Mock
    private ModelToDtoMapper<Offer, OfferResponseDto> offerToOfferResponseDto;

    @Test
    @DisplayName("When new offer that does not exist is created, then will be created")
    public void testCreateOfferWhenOfferNotExists() {

        // given
        OfferDto offerCreateRequestDto = DummyFactory.givenDummyValidOfferDtoNoDetails();
        Offer dummyOfferCreated = new Offer();
        dummyOfferCreated.setOfferId(OFFER_ID);
        OfferResponseDto offerResponseDto = DummyFactory.givenDummyValidOfferResponseDto(dummyLocalDateTime);
        given(offerRepository.findByOfferCode(offerCreateRequestDto.getOfferCode())).willReturn(Optional.empty());
        given(offerRepository.save(any(Offer.class))).willReturn(dummyOfferCreated);
        given(offerDtoToOfferMapper.map(any(OfferDto.class))).willReturn(Optional.of(dummyOfferCreated));
        given(offerToOfferResponseDto.map(any(Offer.class))).willReturn(Optional.of(offerResponseDto));

        // when
        offerService.createOffer(offerCreateRequestDto);

        // then
        verify(offerRepository, times(1)).save(any(Offer.class));
    }

    @Test
    @DisplayName("When create an offer that is already present, then DuplicateResourceException will be thrown")
    public void testCreateOfferWhenOfferAlreadyExists() {
        // given
        OfferDto offerCreateRequestDto = DummyFactory.givenDummyValidOfferDtoNoDetails();
        Offer dummyOffer = new Offer();
        dummyOffer.setOfferId(OFFER_ID);
        dummyOffer.setOfferCode(offerCreateRequestDto.getOfferCode());
        given(offerRepository.findByOfferCode(offerCreateRequestDto.getOfferCode())).willReturn(Optional.of(dummyOffer));

        // when-then
        assertThrows(DuplicateResourceException.class, () -> offerService.createOffer(offerCreateRequestDto));
    }

    @Test
    @DisplayName("When create an offer with an invalid input, then ModelMappingException will be thrown")
    public void testCreateOfferWhenOfferDetailDtoIsNotValid() {
        // given
        OfferDto offerCreateRequestDto = new OfferDto();
        offerCreateRequestDto.setOfferCode("offerCode");
        given(offerRepository.findByOfferCode(offerCreateRequestDto.getOfferCode())).willReturn(Optional.empty());
        given(offerDtoToOfferMapper.map(any(OfferDto.class))).willReturn(Optional.empty());

        // when-then
        assertThrows(ModelMappingException.class, () -> offerService.createOffer(offerCreateRequestDto));
    }

    @Test
    @DisplayName("When update an offer that exists and is not expired, then is successful updated")
    public void testUpdateOfferWhenOfferExists() {
        // given
        OfferDto offerDto = DummyFactory.givenDummyValidOfferDtoNoDetails();
        Offer dummyOfferUpdated = new Offer();
        dummyOfferUpdated.setOfferId(OFFER_ID);
        given(offerRepository.findById(any(UUID.class))).willReturn(Optional.of(dummyOfferUpdated));
        given(offerRepository.save(any(Offer.class))).willReturn(dummyOfferUpdated);
        given(offerDtoToOfferMapper.map(any(OfferDto.class))).willReturn(Optional.of(dummyOfferUpdated));
        given(offerToOfferResponseDto.map(any(Offer.class))).willReturn(Optional.of(new OfferResponseDto(OFFER_ID)));

        // when
        final OfferResponseDto offerResponseDto = offerService.updateOffer(offerDto, OFFER_ID.toString());

        // then
        verify(offerRepository, times(1)).save(any(Offer.class));
    }

    @Test
    @DisplayName("When update an offer does not exist, then ResourceNotFoundException is thrown")
    public void testUpdateOfferWhenOfferDoesNotExist() {
        // given
        OfferDto offerDto = DummyFactory.givenDummyValidOfferDtoNoDetails();
        Offer dummyOfferUpdated = new Offer();
        dummyOfferUpdated.setOfferId(OFFER_ID);
        given(offerRepository.findById(any(UUID.class))).willReturn(Optional.empty());

        // when-then
        assertThrows(ResourceNotFoundException.class, () -> offerService.updateOffer(offerDto, OFFER_ID.toString()));
    }

    @Test
    @DisplayName("When update an offer expired, then OfferExpireException is thrown")
    public void testUpdateOfferWhenOfferIsExpired() {
        // given
        OfferDto offerDto = DummyFactory.givenDummyValidOfferDtoNoDetails();
        LocalDateTime startDate = dummyLocalDateTime.minusSeconds(60);
        LocalDateTime expireDate = dummyLocalDateTime.minusSeconds(30);
        Offer dummyOffer = DummyFactory.givenDummyValidOfferWithExpirationWithoutDetails(startDate, expireDate);
        given(offerRepository.findById(any(UUID.class))).willReturn(Optional.of(dummyOffer));

        // when-then
        assertThrows(OfferExpiredException.class, () -> offerService.updateOffer(offerDto, dummyOffer.getOfferId().toString()));
    }

    @Test
    @DisplayName("When an offer that exists is retrieved, then it will return an offer")
    public void testGetOfferByIdWhenOfferExists() {
        // given
        Offer dummyOffer = new Offer();
        dummyOffer.setOfferId(OFFER_ID);
        given(offerRepository.findById(any(UUID.class))).willReturn(Optional.of(dummyOffer));
        given(offerToOfferResponseDto.map(any(Offer.class))).willReturn(Optional.of(new OfferResponseDto(OFFER_ID)));

        // when
        final OfferResponseDto offerResponseDto = offerService.getOfferById(OFFER_ID.toString());

        // then
        verify(offerRepository, times(1)).findById(any(UUID.class));
        assertNotNull(offerResponseDto);
    }

    @Test
    @DisplayName("When an offer does not exists is retrieved, then ResourceNotFound is thrown")
    public void testGetOfferByIdWhenOfferNotExists() {
        // given
        given(offerRepository.findById(any(UUID.class))).willReturn(Optional.empty());

        // when-then
        assertThrows(ResourceNotFoundException.class, () -> offerService.getOfferById(OFFER_ID.toString()));
    }

    @Test
    @DisplayName("When an offer expired is retrieved, then OfferExpiredException is thrown")
    public void testGetOfferByIdWhenOfferIsExpired() {
        // given
        LocalDateTime startDate = offerClock.get().minusSeconds(60);
        LocalDateTime endDate = offerClock.get().minusSeconds(30);
        Offer dummyOffer = DummyFactory.givenDummyValidOfferWithExpirationWithoutDetails(startDate, endDate);
        given(offerRepository.findById(dummyOffer.getOfferId())).willReturn(Optional.of(dummyOffer));

        // when-then
        assertThrows(OfferExpiredException.class, () -> offerService.getOfferById(dummyOffer.getOfferId().toString()));
    }

    @Test
    @DisplayName("When expires an offer that exists, then the offer expires")
    public void testExpireOfferWhenOfferExists() {
        // given
        Offer dummyOffer = DummyFactory.givenDummyValidOfferNoDetails(OFFER_ID);
        given(offerRepository.findById(dummyOffer.getOfferId())).willReturn(Optional.of(dummyOffer));

        // when
        offerService.expireOfferById(dummyOffer.getOfferId().toString());

        // then
        verify(offerRepository, times(1)).save(dummyOffer);
    }
}
