package com.merchant.store.offers.mapper;

import com.merchant.store.offers.dto.OfferCreateRequestDto;
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
public class OfferCreateRequestDtoToOfferMapperTest {

    private static final LocalDateTime dummyLocalDateTime = LocalDateTime.of(2020, Month.JULY, 12, 10, 0, 0);

    @InjectMocks
    OfferCreateRequestDtoToOfferMapper mapper = new OfferCreateRequestDtoToOfferMapper(() -> dummyLocalDateTime);

    @Test
    @DisplayName("When input is not valid, the return empty Optional")
    public void testMapNonValidInput(){

        // when
        final Optional<Offer> offer = mapper.map(null);

        // then
        assertFalse(offer.isPresent());
    }

    @Test
    @DisplayName("When input has no details nor expiration")
    public void testMapNoExpirationNorDetails(){
        // given
        OfferCreateRequestDto offerCreateRequestDto = DummyFactory.givenDummyValidOfferDtoNoExpirationNorDetails();

        // when
        final Optional<Offer> offer = mapper.map(offerCreateRequestDto);

        // then
        assertTrue(offer.isPresent());
        assertNull(offer.get().getOfferId());
        assertThat(offer.get().getOffersDetailList(), hasSize(0));
        assertEquals(offer.get().getOfferCode(), offerCreateRequestDto.getOfferCode());
        assertEquals(offer.get().getDescription(), offerCreateRequestDto.getDescription());
        assertNull(offer.get().getOfferExpireDate());
        assertEquals(offer.get().getCurrency().name(), offerCreateRequestDto.getCurrency().name());
        assertEquals(offer.get().getPrice(), offerCreateRequestDto.getPrice());
    }

    @Test
    @DisplayName("When input has no details but has expiration")
    public void testMapWithExpirationWithoutDetails(){
        // given
        OfferCreateRequestDto offerCreateRequestDto = DummyFactory.givenDummyValidOfferDtoWithExpirationWithoutDetails();

        // when
        final Optional<Offer> offer = mapper.map(offerCreateRequestDto);

        // then
        assertTrue(offer.isPresent());
        assertNull(offer.get().getOfferId());
        assertThat(offer.get().getOffersDetailList(), hasSize(0));
        assertEquals(offer.get().getOfferCode(), offerCreateRequestDto.getOfferCode());
        assertEquals(offer.get().getDescription(), offerCreateRequestDto.getDescription());
        assertEquals(offer.get().getOfferExpireDate(), dummyLocalDateTime.plusSeconds(offerCreateRequestDto.getExpirationDelay()));
        assertEquals(offer.get().getCurrency().name(), offerCreateRequestDto.getCurrency().name());
        assertEquals(offer.get().getPrice(), offerCreateRequestDto.getPrice());
    }

    @Test
    @DisplayName("When input has expiration and one detail")
    public void testMapWithoutExpirationWithOneDetail(){
        // given
        OfferCreateRequestDto offerCreateRequestDto = DummyFactory.givenDummyValidOfferDtoWithOneDetail();

        // when
        final Optional<Offer> offer = mapper.map(offerCreateRequestDto);

        // then
        assertTrue(offer.isPresent());
        assertNull(offer.get().getOfferId());
        assertEquals(offer.get().getOfferCode(), offerCreateRequestDto.getOfferCode());
        assertEquals(offer.get().getDescription(), offerCreateRequestDto.getDescription());
        assertEquals(offer.get().getOfferExpireDate(), dummyLocalDateTime.plusSeconds(offerCreateRequestDto.getExpirationDelay()));
        assertEquals(offer.get().getCurrency().name(), offerCreateRequestDto.getCurrency().name());
        assertEquals(offer.get().getPrice(), offerCreateRequestDto.getPrice());
        assertThat(offer.get().getOffersDetailList(), hasSize(1));
        assertNull(offer.get().getOffersDetailList().get(0).getOfferDetailId());
        assertEquals(offer.get().getOffersDetailList().get(0).getOfferDetailCode(),
                     offerCreateRequestDto.getOffersDetail().get(0).getOfferDetailCode());
        assertEquals(offer.get().getOffersDetailList().get(0).getOfferDetailDescription(),
                     offerCreateRequestDto.getOffersDetail().get(0).getOfferDetailDescription());
        assertEquals(offer.get().getOffersDetailList().get(0).getQuantity(),
                     offerCreateRequestDto.getOffersDetail().get(0).getQuantity());
    }

}
