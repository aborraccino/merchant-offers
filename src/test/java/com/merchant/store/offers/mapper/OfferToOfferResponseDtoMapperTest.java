package com.merchant.store.offers.mapper;

import com.merchant.store.offers.dto.OfferResponseDto;
import com.merchant.store.offers.model.Offer;
import com.merchant.store.offers.repository.DummyFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OfferToOfferResponseDtoMapperTest {

    private static final LocalDateTime dummyNowLocalDateTime = LocalDateTime.of(2020, Month.JULY, 14, 10, 0, 0);

    @InjectMocks
    OfferToOfferResponseDto mapper = new OfferToOfferResponseDto(() -> dummyNowLocalDateTime);

    @Test
    @DisplayName("When input is not valid, the return empty Optional")
    public void testMapNonValidInput(){

        // when
        final Optional<OfferResponseDto> offerResponseDto = mapper.map(null);

        // then
        assertFalse(offerResponseDto.isPresent());
    }

    @Test
    @DisplayName("When input has no details nor expiration")
    public void testMapNoExpirationNorDetails(){
        // given
        Offer offer = DummyFactory.givenDummyValidOfferNoExpirationNorDetails();

        // when
        final Optional<OfferResponseDto> offerResponseDto = mapper.map(offer);

        // then
        assertTrue(offerResponseDto.isPresent());
        assertNull(offerResponseDto.get().getOfferId());
        assertThat(offerResponseDto.get().getOffersDetail(), hasSize(0));
        assertEquals(offer.getOfferCode(), offerResponseDto.get().getOfferCode());
        assertEquals(offer.getDescription(), offerResponseDto.get().getDescription());
        assertNull(offerResponseDto.get().getOfferExpireDate());
        assertEquals(offer.getCurrency().name(), offerResponseDto.get().getCurrency().name());
        assertEquals(offer.getPrice(), offerResponseDto.get().getPrice());
    }

    @Test
    @DisplayName("When input has no details and is not expired")
    public void testMapWithoutDetailsNotExpired(){
        // given
        LocalDateTime startDate = dummyNowLocalDateTime.minusSeconds(60);
        LocalDateTime expireDate = dummyNowLocalDateTime.plusSeconds(60);
        Offer offer = DummyFactory.givenDummyValidOfferWithExpirationWithoutDetails(startDate, expireDate);

        // when
        final Optional<OfferResponseDto> offerResponseDto = mapper.map(offer);

        // then
        assertTrue(offerResponseDto.isPresent());
        assertNull(offerResponseDto.get().getOfferId());
        assertThat(offerResponseDto.get().getOffersDetail(), hasSize(0));
        assertEquals(offerResponseDto.get().getOfferCode(), offer.getOfferCode());
        assertEquals(offerResponseDto.get().getDescription(), offer.getDescription());
        assertFalse(offerResponseDto.get().isExpired());
        assertEquals(offerResponseDto.get().getOfferStartDate(), startDate);
        assertEquals(offerResponseDto.get().getOfferExpireDate(), expireDate);
        assertEquals(offerResponseDto.get().getCurrency().name(), offer.getCurrency().name());
        assertEquals(offerResponseDto.get().getPrice(), offer.getPrice());
    }

    @Test
    @DisplayName("When input has no details and is expired")
    public void testMapWithoutDetailsExpired(){
        // given
        LocalDateTime startDate = dummyNowLocalDateTime.minusSeconds(60);
        LocalDateTime expireDate = dummyNowLocalDateTime.minusSeconds(30);
        Offer offer = DummyFactory.givenDummyValidOfferWithExpirationWithoutDetails(startDate, expireDate);

        // when
        final Optional<OfferResponseDto> offerResponseDto = mapper.map(offer);

        // then
        assertTrue(offerResponseDto.isPresent());
        assertNull(offerResponseDto.get().getOfferId());
        assertThat(offerResponseDto.get().getOffersDetail(), hasSize(0));
        assertEquals(offerResponseDto.get().getOfferCode(), offer.getOfferCode());
        assertEquals(offerResponseDto.get().getDescription(), offer.getDescription());
        assertTrue(offerResponseDto.get().isExpired());
        assertEquals(offerResponseDto.get().getOfferStartDate(), startDate);
        assertEquals(offerResponseDto.get().getOfferExpireDate(), expireDate);
        assertEquals(offerResponseDto.get().getCurrency().name(), offer.getCurrency().name());
        assertEquals(offerResponseDto.get().getPrice(), offer.getPrice());
    }

    @Test
    @DisplayName("When the input has one detail")
    public void testMapWithOneDetail(){
        // given
        Offer offer = DummyFactory.givenDummyOfferValidWithOneDetail();

        // when
        final Optional<OfferResponseDto> offerResponseDto = mapper.map(offer);

        // then
        assertTrue(offerResponseDto.isPresent());
        assertNull(offerResponseDto.get().getOfferId());
        assertEquals(offerResponseDto.get().getOfferCode(), offer.getOfferCode());
        assertEquals(offerResponseDto.get().getDescription(), offer.getDescription());
        assertEquals(offerResponseDto.get().getCurrency().name(), offer.getCurrency().name());
        assertEquals(offerResponseDto.get().getPrice(), offer.getPrice());
        assertThat(offerResponseDto.get().getOffersDetail(), hasSize(1));
        assertEquals(offerResponseDto.get().getOffersDetail().get(0).getOfferDetailCode(),
                     offer.getOffersDetailList().get(0).getOfferDetailCode());
        assertEquals(offerResponseDto.get().getOffersDetail().get(0).getOfferDetailDescription(),
                     offer.getOffersDetailList().get(0).getOfferDetailDescription());
        assertEquals(offerResponseDto.get().getOffersDetail().get(0).getQuantity(),
                     offer.getOffersDetailList().get(0).getQuantity());
    }

}
