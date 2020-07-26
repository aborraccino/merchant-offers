package com.merchant.store.offers.mapper;

import com.merchant.store.offers.dto.OfferDetailResponseDto;
import com.merchant.store.offers.model.OffersDetail;
import com.merchant.store.offers.repository.DummyFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OfferDetailToOfferDetailDtoMapperTest {

    @InjectMocks
    private OfferDetailToOfferDetailDtoMapper mapper;

    @Test
    @DisplayName("When input is not valid, the return empty Optional")
    public void testMapNonValidInput(){

        // when
        final Optional<OfferDetailResponseDto> offer = mapper.map(null);

        // then
        assertFalse(offer.isPresent());
    }

    @Test
    @DisplayName("When input is valid, the return OfferDetailResponseDto")
    public void testMapValidInput(){
        // given
        OffersDetail dummyOffersDetail = DummyFactory.givenDummyOfferDetailNoMainOffer();

        // when
        final Optional<OfferDetailResponseDto> offerDetailResponseDto = mapper.map(dummyOffersDetail);

        // then
        assertTrue(offerDetailResponseDto.isPresent());
        assertEquals(dummyOffersDetail.getOfferDetailId(), offerDetailResponseDto.get().getOfferDetailId());
        assertEquals(dummyOffersDetail.getOfferDetailCode(), offerDetailResponseDto.get().getOfferDetailCode());
        assertEquals(dummyOffersDetail.getOfferDetailDescription(), offerDetailResponseDto.get().getOfferDetailDescription());
        assertEquals(dummyOffersDetail.getQuantity(), offerDetailResponseDto.get().getQuantity());
    }
}
