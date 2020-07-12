package com.merchant.store.offers.repository;

import com.merchant.store.offers.model.Offer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface OfferRepository extends CrudRepository<Offer, UUID> {

    Optional<Offer> findByOfferCode(String code);
}
