package com.merchant.store.offers.mapper;

import com.merchant.store.offers.dto.CurrencyEnumDto;
import com.merchant.store.offers.dto.OfferResponseDto;
import com.merchant.store.offers.dto.OffersDetailCreateRequestDto;
import com.merchant.store.offers.model.Offer;
import com.merchant.store.offers.model.OffersDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OfferToOfferResponseDto implements ModelToDtoMapper<Offer, OfferResponseDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfferToOfferResponseDto.class);

    private Supplier<LocalDateTime> offerClock;

    @Autowired
    public OfferToOfferResponseDto(Supplier<LocalDateTime> offerClock) {
        this.offerClock = offerClock;
    }

    @Override
    public Optional<OfferResponseDto> map(Offer offer) {

        if (Objects.isNull(offer)) {
            return Optional.empty();
        }

        LOGGER.info("mapping offer to offerDto {}", offer);

        OfferResponseDto offerResponseDto = new OfferResponseDto(offer.getOfferId())
                .setOfferCode(offer.getOfferCode())
                .setDescription(offer.getDescription())
                .setCurrency(CurrencyEnumDto.valueOf(offer.getCurrency().name()))
                .setPrice(offer.getPrice())
                .setOfferStartDate(offer.getOfferStartDate())
                .setOfferExpireDate(offer.getOfferExpireDate());

        // set flag is expired
        if (Objects.nonNull(offer.getOfferExpireDate())) {
            offerResponseDto.setExpired(offerClock.get().isAfter(offer.getOfferExpireDate()));
        }

        final Stream<OffersDetail> offersDetailStream = Optional.ofNullable(offer.getOffersDetailList())
                        .map(Collection::stream)
                        .orElseGet(Stream::empty);

        offerResponseDto.setOffersDetail(offersDetailStream
                                                 .filter(Objects::nonNull)
                                                 .map(offersDetail -> {
                                                     OffersDetailCreateRequestDto offersDetailDto =
                                                             new OffersDetailCreateRequestDto();
                                                     offersDetailDto.setOfferDetailDescription(
                                                             offersDetail.getOfferDetailDescription());
                                                     offersDetailDto.setOfferDetailCode(
                                                             offersDetail.getOfferDetailCode());
                                                     offersDetailDto.setQuantity(
                                                             offersDetail.getQuantity());
                                                     return offersDetailDto;
                                                 })
                                                 .collect(Collectors.toList()));

        return Optional.of(offerResponseDto);
    }
}
