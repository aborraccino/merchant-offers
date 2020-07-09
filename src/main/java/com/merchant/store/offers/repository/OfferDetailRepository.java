package com.merchant.store.offers.repository;

import com.merchant.store.offers.model.OffersDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OfferDetailRepository extends CrudRepository<OffersDetail, UUID> {

}
