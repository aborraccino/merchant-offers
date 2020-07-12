package com.merchant.store.offers.service.impl;

import com.merchant.store.offers.dto.OfferCreateRequestDto;
import com.merchant.store.offers.exception.DuplicateResourceException;
import com.merchant.store.offers.exception.ModelMappingException;
import com.merchant.store.offers.mapper.DtoToModelMapper;
import com.merchant.store.offers.mapper.OfferCreateRequestDtoToOfferMapper;
import com.merchant.store.offers.model.Offer;
import com.merchant.store.offers.repository.DummyFactory;
import com.merchant.store.offers.repository.OfferRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OfferServiceImplTest {

    @InjectMocks
    private OfferServiceImpl offerService;

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private DtoToModelMapper<OfferCreateRequestDto, Offer> offerCreateRequestDtoToOfferMapper = new OfferCreateRequestDtoToOfferMapper(
            LocalDateTime::now);

    @Test
    @DisplayName("When new offer that does not exist is created, then will be created")
    public void testCreateOfferWhenOfferNotExists() {

        // given
        OfferCreateRequestDto offerCreateRequestDto = DummyFactory.givenDummyValidOfferDtoNoDetails();
        Offer dummyOfferCreated = new Offer();
        dummyOfferCreated.setOfferId(UUID.randomUUID());
        given(offerRepository.findByOfferCode(offerCreateRequestDto.getOfferCode())).willReturn(Optional.empty());
        given(offerRepository.save(any(Offer.class))).willReturn(dummyOfferCreated);
        given(offerCreateRequestDtoToOfferMapper.map(any(OfferCreateRequestDto.class))).willReturn(Optional.of(dummyOfferCreated));

        // when
        offerService.createOffer(offerCreateRequestDto);

        // then
        verify(offerRepository, times(1)).save(any(Offer.class));
    }

    @Test
    @DisplayName("When create an offer that is already present, then DuplicateResourceException will be thrown")
    public void testCreateOfferWhenOfferAlreadyExists() {
        // given
        OfferCreateRequestDto offerCreateRequestDto = DummyFactory.givenDummyValidOfferDtoNoDetails();
        Offer dummyOffer = new Offer();
        dummyOffer.setOfferId(UUID.randomUUID());
        dummyOffer.setOfferCode(offerCreateRequestDto.getOfferCode());
        given(offerRepository.findByOfferCode(offerCreateRequestDto.getOfferCode())).willReturn(Optional.of(dummyOffer));

        // when-then
        assertThrows(DuplicateResourceException.class, () -> offerService.createOffer(offerCreateRequestDto));
    }

    @Test
    @DisplayName("When create an offer with an invalid input, then ModelMappingException will be thrown")
    public void testCreateOfferWhenOfferDetailDtoIsNotValid() {
        // given
        OfferCreateRequestDto offerCreateRequestDto = new OfferCreateRequestDto();
        offerCreateRequestDto.setOfferCode("offerCode");
        given(offerRepository.findByOfferCode(offerCreateRequestDto.getOfferCode())).willReturn(Optional.empty());
        given(offerCreateRequestDtoToOfferMapper.map(any(OfferCreateRequestDto.class))).willReturn(Optional.empty());

        // when-then
        assertThrows(ModelMappingException.class, () -> offerService.createOffer(offerCreateRequestDto));
    }
}
