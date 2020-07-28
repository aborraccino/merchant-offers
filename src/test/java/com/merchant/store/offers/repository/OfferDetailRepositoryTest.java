package com.merchant.store.offers.repository;

import com.merchant.store.offers.model.Offer;
import com.merchant.store.offers.model.OffersDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OfferDetailRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private OfferDetailRepository offerDetailRepository;

    @Test
    @DisplayName("When a valid offer detail is saved and associated to a valid offer, then can be retrieved")
    public void testSaveOfferDetailWhenMainOfferExists() {
        // given
        Offer dummyOffer = DummyFactory.givenDummyValidOfferNoDetails(null);
        testEntityManager.persist(dummyOffer);
        testEntityManager.flush();
        OffersDetail dummyOffersDetail = DummyFactory.givenDummyOfferDetail(dummyOffer, null);
        testEntityManager.persist(dummyOffersDetail);
        testEntityManager.flush();

        // when
        Optional<OffersDetail> offersDetail = offerDetailRepository.findById(dummyOffersDetail.getOfferDetailId());

        // then
        assertTrue(offersDetail.isPresent());
        assertEquals(offersDetail.get().getOfferDetailId(), dummyOffersDetail.getOfferDetailId());
    }

    @Test
    @DisplayName("When a valid offer detail is saved and no association to a valid offer exists,"
            + " then Persistence exception will be thrown")
    public void testSaveOfferDetailWhenMainOfferNotExists() {

        // given
        OffersDetail dummyOffersDetail = DummyFactory.givenDummyOfferDetailNoMainOffer();

        // when then
        assertThrows(PersistenceException.class, () -> {
            testEntityManager.persist(dummyOffersDetail);
            testEntityManager.flush();
        });
    }

    @Test
    @DisplayName("When an offer detail is saved without code,"
            + " then Persistence exception will be thrown")
    public void testSaveOfferDetailWhenIsMissingOfferDetailCode() {

        // given
        Offer dummyOffer = DummyFactory.givenDummyValidOfferNoDetails(null);
        testEntityManager.persist(dummyOffer);
        testEntityManager.flush();
        OffersDetail dummyOffersDetail = DummyFactory.givenDummyOfferDetailWithoutCode(dummyOffer);

        // when then
        assertThrows(PersistenceException.class, () -> {
            testEntityManager.persist(dummyOffersDetail);
            testEntityManager.flush();
        });
    }

    @Test
    @DisplayName("When an offer detail is saved without quantity,"
            + " then Persistence exception will be thrown")
    public void testSaveOfferDetailWhenIsMissingQuantity() {

        // given
        Offer dummyOffer = DummyFactory.givenDummyValidOfferNoDetails(null);
        testEntityManager.persist(dummyOffer);
        testEntityManager.flush();
        OffersDetail dummyOffersDetail = DummyFactory.givenDummyValidOfferDetailNoQuantity(dummyOffer);

        // when then
        assertThrows(PersistenceException.class, () -> {
            testEntityManager.persist(dummyOffersDetail);
            testEntityManager.flush();
        });
    }
}
