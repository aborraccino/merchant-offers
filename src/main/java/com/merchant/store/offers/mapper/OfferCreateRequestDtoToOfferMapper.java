package com.merchant.store.offers.mapper;

import com.merchant.store.offers.dto.OfferCreateRequestDto;
import com.merchant.store.offers.model.CurrencyEnum;
import com.merchant.store.offers.model.Offer;
import com.merchant.store.offers.model.OffersDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class OfferCreateRequestDtoToOfferMapper implements DtoToModelMapper<OfferCreateRequestDto, Offer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfferCreateRequestDtoToOfferMapper.class);

    private Supplier<LocalDateTime> offerClock;

    @Autowired
    public OfferCreateRequestDtoToOfferMapper(Supplier<LocalDateTime> offerClock) {
        this.offerClock = offerClock;
    }

    @Override
    public Optional<Offer> map(OfferCreateRequestDto offerCreateRequestDto) {

        if(Objects.isNull(offerCreateRequestDto)){
            LOGGER.warn("offerCreateRequestDto is null!");
            return Optional.empty();
        }

        Offer offer = new Offer();
        LocalDateTime offerStartDate = offerClock.get();
        offer.setOfferCode(offerCreateRequestDto.getOfferCode())
                .setOfferStartDate(offerStartDate)
                .setPrice(offerCreateRequestDto.getPrice())
                .setDescription(offerCreateRequestDto.getDescription())
                .setCurrency(CurrencyEnum.valueOf(offerCreateRequestDto.getCurrency().name()));

        // if is present expiration delay, calculate the end date
        if(Objects.nonNull(offerCreateRequestDto.getExpirationDelay())){
            LocalDateTime offerExpireDate = offerStartDate.plusSeconds(offerCreateRequestDto.getExpirationDelay());
            offer.setOfferExpireDate(offerExpireDate);
        }

        if(!CollectionUtils.isEmpty(offerCreateRequestDto.getOffersDetail())){
            List<OffersDetail> offersDetailList = offerCreateRequestDto.getOffersDetail()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(offersDetailCreateRequestDto -> {
                        OffersDetail offersDetail = new OffersDetail();
                        offersDetail.setOffer(offer);
                        offersDetail.setOfferDetailDescription(offersDetailCreateRequestDto.getOfferDetailDescription());
                        offersDetail.setOfferDetailCode(offersDetailCreateRequestDto.getOfferDetailCode());
                        offersDetail.setQuantity(offersDetailCreateRequestDto.getQuantity());
                        return offersDetail;
                    })
                    .collect(Collectors.toList());

            offer.setOffersDetailList(offersDetailList);
        }

        return Optional.of(offer);
    }
}

