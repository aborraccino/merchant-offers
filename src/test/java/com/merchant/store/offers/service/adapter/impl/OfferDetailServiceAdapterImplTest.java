package com.merchant.store.offers.service.adapter.impl;

import com.merchant.store.offers.dto.OfferResponseDto;
import com.merchant.store.offers.dto.OffersDetailDto;
import com.merchant.store.offers.exception.ResourceNotFoundException;
import com.merchant.store.offers.repository.DummyFactory;
import com.merchant.store.offers.service.OfferDetailService;
import com.merchant.store.offers.service.OfferService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OfferDetailServiceAdapterImplTest {

    private static final UUID OFFER_ID = UUID.fromString("3db46740-71d3-4881-8a0e-7206f9e1c089");
    private static final UUID OFFER_DETAIL_ID = UUID.fromString("177d72da-2996-40bb-9396-ddf13c40c978");

    @InjectMocks
    private OfferDetailServiceAdapterImpl offerDetailServiceAdapter;

    @Mock
    private OfferDetailService offerDetailService;

    @Mock
    private OfferService offerService;

    @Test
    @DisplayName("When offer detail is saved and the parent exists, then is saved successfully")
    public void testAddOfferDetailWhenOfferExists() {
        // given
        OfferResponseDto dummyOfferResponseDto = DummyFactory.givenDummyValidOfferResponseDtoNoDetails(null);
        OffersDetailDto offerDetailDto = DummyFactory.givenDummyOfferDetailDto();
        given(offerService.getOfferById(OFFER_ID.toString())).willReturn(dummyOfferResponseDto);
        given(offerDetailService.addOfferDetail(any(OffersDetailDto.class))).willReturn(OFFER_DETAIL_ID);

        // when
        UUID addOfferDetailUuid = offerDetailServiceAdapter.addOfferDetail(OFFER_ID.toString(), offerDetailDto);

        // verify
        verify(offerDetailService).addOfferDetail(any(OffersDetailDto.class));
        assertNotNull(addOfferDetailUuid);
    }

    @Test
    @DisplayName("When offer detail is saved and the parent does not exist, then ResourceNotFoundException is thrown")
    public void testAddOfferDetailWhenOfferNotExists() {
        // given
        OffersDetailDto offerDetailDto = DummyFactory.givenDummyOfferDetailDto();
        given(offerService.getOfferById(OFFER_ID.toString())).willThrow(ResourceNotFoundException.class);

        // when-then
        assertThrows(ResourceNotFoundException.class, () -> offerDetailServiceAdapter.addOfferDetail(OFFER_ID.toString(), offerDetailDto));
    }

    @Test
    @DisplayName("When offer is updated and the parent exists, then is updated")
    public void testUpdateOfferDetailWhenOfferExists() {
        // given
        OfferResponseDto dummyOfferResponseDto = DummyFactory.givenDummyValidOfferResponseDtoNoDetails(null);
        OffersDetailDto offerDetailDto = DummyFactory.givenDummyOfferDetailDto();
        given(offerService.getOfferById(OFFER_ID.toString())).willReturn(dummyOfferResponseDto);
        doNothing().when(offerDetailService).updateOfferDetail(anyString(), any(OffersDetailDto.class));

        // when
        offerDetailServiceAdapter.updateOfferDetail(OFFER_ID.toString(), OFFER_DETAIL_ID.toString(), offerDetailDto);

        // verify
        verify(offerDetailService).updateOfferDetail(anyString(), any(OffersDetailDto.class));
    }

    @Test
    @DisplayName("When offer is updated and the parent does not exist, then ResourceNotFoundException is thrown")
    public void testUpdateOfferDetailWhenOfferNotExists() {
        // given
        OffersDetailDto offerDetailDto = DummyFactory.givenDummyOfferDetailDto();
        given(offerService.getOfferById(OFFER_ID.toString())).willThrow(ResourceNotFoundException.class);

        // when-then
        assertThrows(ResourceNotFoundException.class, () -> offerDetailServiceAdapter.updateOfferDetail(OFFER_ID.toString(),
                                                                                                        OFFER_DETAIL_ID.toString(),
                                                                                                        offerDetailDto));
    }

    @Test
    @DisplayName("When offer is retrieved and the parent exists, then is retrieved")
    public void testGetOfferDetailWhenOfferExists() {
        // given
        OfferResponseDto dummyOfferResponseDto = DummyFactory.givenDummyValidOfferResponseDtoNoDetails(null);
        given(offerService.getOfferById(OFFER_ID.toString())).willReturn(dummyOfferResponseDto);

        // when
        offerDetailServiceAdapter.getOfferDetail(OFFER_ID.toString(), OFFER_DETAIL_ID.toString());

        // verify
        verify(offerDetailService).getOfferDetail(anyString());
    }

    @Test
    @DisplayName("When offer is retrieved and the parent does not exist, then ResourceNotFoundException is thrown")
    public void testGetOfferDetailWhenOfferNotExists() {
        // given
        given(offerService.getOfferById(anyString())).willThrow(ResourceNotFoundException.class);

        // when-then
        assertThrows(ResourceNotFoundException.class, () -> offerDetailServiceAdapter.getOfferDetail(OFFER_ID.toString(), OFFER_DETAIL_ID.toString()));
    }
}
