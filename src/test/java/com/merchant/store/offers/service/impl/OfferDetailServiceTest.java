package com.merchant.store.offers.service.impl;

import com.merchant.store.offers.dto.OfferDetailResponseDto;
import com.merchant.store.offers.dto.OffersDetailDto;
import com.merchant.store.offers.exception.DuplicateResourceException;
import com.merchant.store.offers.exception.ResourceNotFoundException;
import com.merchant.store.offers.mapper.OfferDetailDtoToOfferDetailMapper;
import com.merchant.store.offers.mapper.OfferDetailToOfferDetailDtoMapper;
import com.merchant.store.offers.model.OffersDetail;
import com.merchant.store.offers.repository.DummyFactory;
import com.merchant.store.offers.repository.OfferDetailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OfferDetailServiceTest {

    @InjectMocks
    private OfferDetailServiceImpl offerDetailService;

    @Mock
    private OfferDetailRepository offerDetailRepository;

    @Mock
    private OfferDetailDtoToOfferDetailMapper offerDetailDtoToOfferDetailMapper;

    @Mock
    private OfferDetailToOfferDetailDtoMapper offerDetailToOfferDetailDtoMapper;

    private static final UUID OFFER_DETAIL_ID = UUID.fromString("177d72da-2996-40bb-9396-ddf13c40c978");

    @Test
    @DisplayName("When offer detail does not exists, then create new one")
    public void testAddOfferDetailWhenItemNotExists() {
        // given
        OffersDetailDto offerDetailDto = DummyFactory.givenDummyOfferDetailDto();
        OffersDetail dummyOfferDetail = DummyFactory.givenDummyOfferDetailNoMainOffer();
        dummyOfferDetail.setOfferDetailId(OFFER_DETAIL_ID);
        given(offerDetailRepository.findByOfferDetailCode(anyString())).willReturn(Optional.empty());
        given(offerDetailDtoToOfferDetailMapper.map(any(OffersDetailDto.class))).willReturn(Optional.of(dummyOfferDetail));
        given(offerDetailRepository.save(any(OffersDetail.class))).willReturn(dummyOfferDetail);

        // when
        final UUID offerDetailUuid = offerDetailService.addOfferDetail(offerDetailDto);

        // then
        assertNotNull(offerDetailUuid);
        verify(offerDetailRepository).save(any(OffersDetail.class));
    }


    @Test
    @DisplayName("When offer detail exists, then DuplicateResourceException is thrown")
    public void testAddOfferDetailWhenItemExists() {
        // given
        OffersDetailDto offerDetailDto = DummyFactory.givenDummyOfferDetailDto();
        OffersDetail dummyOfferDetail = DummyFactory.givenDummyOfferDetailNoMainOffer();
        dummyOfferDetail.setOfferDetailId(OFFER_DETAIL_ID);
        given(offerDetailRepository.findByOfferDetailCode(anyString())).willReturn(Optional.of(dummyOfferDetail));

        // when-then
        assertThrows(DuplicateResourceException.class, () -> offerDetailService.addOfferDetail(offerDetailDto));
        verify(offerDetailRepository, times(0)).save(any(OffersDetail.class));
    }


    @Test
    @DisplayName("When offer detail exists, then return the offer detail")
    public void testGetOfferDetailByIdWhenItemExists() {
        // given
        OfferDetailResponseDto offerDetailResponseDto = DummyFactory.givenDummyOfferDetailResponseDto();
        OffersDetail dummyOfferDetail = DummyFactory.givenDummyOfferDetailNoMainOffer();
        dummyOfferDetail.setOfferDetailId(OFFER_DETAIL_ID);
        given(offerDetailRepository.findById(any(UUID.class))).willReturn(Optional.of(dummyOfferDetail));
        given(offerDetailToOfferDetailDtoMapper.map(any(OffersDetail.class))).willReturn(Optional.of(offerDetailResponseDto));

        // when
        final OfferDetailResponseDto responseDto = offerDetailService.getOfferDetail(OFFER_DETAIL_ID.toString());

        // then
        assertNotNull(responseDto);
    }


    @Test
    @DisplayName("When offer detail does not exist, then ResourceNotFoundException is thrown")
    public void testGetOfferDetailByIdWhenItemNotExists() {
        // given
        given(offerDetailRepository.findById(any(UUID.class))).willReturn(Optional.empty());

        // when-then
        assertThrows(ResourceNotFoundException.class, () -> offerDetailService.getOfferDetail(OFFER_DETAIL_ID.toString()));
    }

    @Test
    @DisplayName("When offer detail exists and is updated, then is updated successfully")
    public void testUpdateOfferDetailWhenOfferDetailExists() {
        // given
        OffersDetailDto offerDetailDto = DummyFactory.givenDummyOfferDetailDto();
        OffersDetail dummyOfferDetail = DummyFactory.givenDummyOfferDetailNoMainOffer();
        dummyOfferDetail.setOfferDetailId(OFFER_DETAIL_ID);
        given(offerDetailRepository.findById(any(UUID.class))).willReturn(Optional.of(dummyOfferDetail));
        given(offerDetailDtoToOfferDetailMapper.map(any(OffersDetailDto.class))).willReturn(
                Optional.of(dummyOfferDetail));

        // when
        offerDetailService.updateOfferDetail(OFFER_DETAIL_ID.toString(), offerDetailDto);

        // then
        verify(offerDetailRepository).save(any(OffersDetail.class));
    }

    @Test
    @DisplayName("When offer detail does not exist and is updated, then ResourceNotFoundException is thrown")
    public void testUpdateOfferDetailWhenItemNotExists() {
        // given
        OffersDetailDto offerDetailDto = DummyFactory.givenDummyOfferDetailDto();
        given(offerDetailRepository.findById(any(UUID.class))).willReturn(Optional.empty());

        // when-then
        assertThrows(ResourceNotFoundException.class, () -> offerDetailService.updateOfferDetail(OFFER_DETAIL_ID.toString(),
                                                                                                 offerDetailDto));
    }
}
