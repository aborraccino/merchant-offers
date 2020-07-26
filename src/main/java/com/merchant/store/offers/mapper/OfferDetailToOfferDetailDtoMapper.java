package com.merchant.store.offers.mapper;

import com.merchant.store.offers.dto.OfferDetailResponseDto;
import com.merchant.store.offers.model.OffersDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class OfferDetailToOfferDetailDtoMapper implements ModelToDtoMapper<OffersDetail, OfferDetailResponseDto>{

    private static final Logger LOGGER = LoggerFactory.getLogger(OfferDetailToOfferDetailDtoMapper.class);

    @Override
    public Optional<OfferDetailResponseDto> map(OffersDetail offersDetail) {

        if(Objects.isNull(offersDetail)){
            LOGGER.warn("offersDetail is null!");
            return Optional.empty();
        }

        return Optional.of(new OfferDetailResponseDto(offersDetail.getOfferDetailId())
                                   .setOfferDetailCode(offersDetail.getOfferDetailCode())
                                   .setOfferDetailDescription(offersDetail.getOfferDetailDescription())
                                   .setQuantity(offersDetail.getQuantity()));
    }
}
