package com.merchant.store.offers.repository;

import com.merchant.store.offers.model.Offer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class OfferRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private OfferRepository offerRepository;

    @Test
    @DisplayName("When a valid offer is saved, then can be retrieved")
    public void testSaveValidOffer() {
        // given
        Offer dummyOffer = DummyFactory.givenDummyValidOfferNoDetails();
        testEntityManager.persist(dummyOffer);
        testEntityManager.flush();

        // when
        Optional<Offer> offer = offerRepository.findById(dummyOffer.getOfferId());

        // then
        assertTrue(offer.isPresent());
        assertEquals(dummyOffer.getOfferId(), offer.get().getOfferId());
    }

    @Test
    @DisplayName("When a valid offer with one associated detail is saved, the can be retrieved")
    public void testSaveValidOfferWithOneDetail() {
        // given
        Offer dummyOffer = DummyFactory.givenDummyOfferValidWithOneDetail();
        testEntityManager.persist(dummyOffer);
        testEntityManager.flush();

        // when
        Optional<Offer> offer = offerRepository.findById(dummyOffer.getOfferId());

        // then
        assertTrue(offer.isPresent());
        assertEquals(dummyOffer.getOfferId(), offer.get().getOfferId());
        assertThat(offer.get().getOffersDetailList().size(), is(1));
        assertEquals(offer.get().getOffersDetailList().get(0).getOfferDetailId(), dummyOffer.getOffersDetailList().get(0).getOfferDetailId());
    }

    @Test
    @DisplayName("When an offer is not valid because is missing code, then PersistenceException will be thrown")
    public void testValidationWhenIsMissingCode() {
        // given
        Offer invalidOffer = DummyFactory.givenDummyInvalidOfferValidWithoutCode();

        // when-then
        assertThrows(PersistenceException.class, () -> {
            testEntityManager.persist(invalidOffer);
            testEntityManager.flush();
        });
    }

    @Test
    @DisplayName("When an offer is not valid because is missing price, then PersistenceException will be thrown")
    public void testValidationWhenIsMissingPrice() {
        // given
        Offer invalidOffer = DummyFactory.givenDummyInvalidOfferValidWithoutPrice();

        // when
        assertThrows(PersistenceException.class, () -> {
            testEntityManager.persist(invalidOffer);
            testEntityManager.flush();
        });
    }

    @Test
    @DisplayName("When an offer is not valid because is missing price, then PersistenceException will be thrown")
    public void testValidationWhenIsMissingCurrency() {
        // given
        Offer invalidOffer = DummyFactory.givenDummyInvalidOfferValidWithoutCurrency();

        // when
        assertThrows(PersistenceException.class, () -> {
            testEntityManager.persist(invalidOffer);
            testEntityManager.flush();
        });
    }
}
