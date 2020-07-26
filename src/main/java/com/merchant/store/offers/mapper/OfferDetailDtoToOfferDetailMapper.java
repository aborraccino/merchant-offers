package com.merchant.store.offers.mapper;

import com.merchant.store.offers.dto.OffersDetailDto;
import com.merchant.store.offers.model.Offer;
import com.merchant.store.offers.model.OffersDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class OfferDetailDtoToOfferDetailMapper implements DtoToModelMapper<OffersDetailDto, OffersDetail> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfferDetailDtoToOfferDetailMapper.class);

    @Override
    public Optional<OffersDetail> map(OffersDetailDto offersDetailDto) {

        if(Objects.isNull(offersDetailDto)){
            LOGGER.warn("offersDetailDto is null!");
            return Optional.empty();
        }

        OffersDetail offersDetail = new OffersDetail();
        offersDetail.setOfferDetailCode(offersDetailDto.getOfferDetailCode());
        offersDetail.setOfferDetailDescription(offersDetailDto.getOfferDetailDescription());
        offersDetail.setQuantity(offersDetailDto.getQuantity());
        offersDetail.setOffer(new Offer(offersDetailDto.getOfferId()));
        return Optional.of(offersDetail);
    }
}
