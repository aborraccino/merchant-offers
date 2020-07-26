package com.merchant.store.offers.mapper;

import com.merchant.store.offers.dto.OffersDetailDto;
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
public class OfferDetailDtoToOfferDetailMapperTest {

    @InjectMocks
    private OfferDetailDtoToOfferDetailMapper mapper;

    @Test
    @DisplayName("When input is not valid, the return empty Optional")
    public void testMapNonValidInput(){

        // when
        final Optional<OffersDetail> offer = mapper.map(null);

        // then
        assertFalse(offer.isPresent());
    }

    @Test
    @DisplayName("When input is valid, the return OfferDetailResponseDto")
    public void testMapValidInput(){
        // given
        OffersDetailDto dummyOffersDetailDto = DummyFactory.givenDummyOfferDetailDto();

        // when
        final Optional<OffersDetail> offersDetail = mapper.map(dummyOffersDetailDto);

        // then
        assertTrue(offersDetail.isPresent());
        assertEquals(dummyOffersDetailDto.getOfferDetailCode(), offersDetail.get().getOfferDetailCode());
        assertEquals(dummyOffersDetailDto.getOfferDetailDescription(), offersDetail.get().getOfferDetailDescription());
        assertEquals(dummyOffersDetailDto.getQuantity(), offersDetail.get().getQuantity());
    }
}
