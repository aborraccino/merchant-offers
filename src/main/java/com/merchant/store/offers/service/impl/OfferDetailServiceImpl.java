package com.merchant.store.offers.service.impl;

import com.merchant.store.offers.dto.OfferDetailResponseDto;
import com.merchant.store.offers.dto.OffersDetailDto;
import com.merchant.store.offers.exception.DuplicateResourceException;
import com.merchant.store.offers.exception.ModelMappingException;
import com.merchant.store.offers.exception.ResourceNotFoundException;
import com.merchant.store.offers.mapper.OfferDetailDtoToOfferDetailMapper;
import com.merchant.store.offers.mapper.OfferDetailToOfferDetailDtoMapper;
import com.merchant.store.offers.model.OffersDetail;
import com.merchant.store.offers.repository.OfferDetailRepository;
import com.merchant.store.offers.service.OfferDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class OfferDetailServiceImpl implements OfferDetailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfferDetailServiceImpl.class);

    @Autowired
    private OfferDetailRepository offerDetailRepository;

    @Autowired
    private OfferDetailDtoToOfferDetailMapper offerDetailDtoToOfferDetailMapper;

    @Autowired
    private OfferDetailToOfferDetailDtoMapper offerDetailToOfferDetailDtoMapper;

    @Override
    @Transactional
    public UUID addOfferDetail(OffersDetailDto offersDetailDto) {
        LOGGER.info("addOfferDetail(code={})", offersDetailDto.getOfferDetailCode());

        Optional<OffersDetail> offersDetail = offerDetailRepository.findByOfferDetailCode(offersDetailDto.getOfferDetailCode());

        if (offersDetail.isPresent()) {
            LOGGER.error("offer detail with code {} is already present", offersDetailDto.getOfferDetailCode());
            throw new DuplicateResourceException("Offer detail is already present, code= " + offersDetailDto.getOfferDetailCode());
        }

        OffersDetail newOffersDetail = offerDetailDtoToOfferDetailMapper.map(offersDetailDto).orElseThrow(
                () -> new ModelMappingException("offerDetailDtoToOfferDetailMapper was unable to convert the model")
        );
        final OffersDetail detail = offerDetailRepository.save(newOffersDetail);
        LOGGER.info("new offer detail created = {}", detail);
        return detail.getOfferDetailId();
    }

    @Override
    @Transactional
    public void updateOfferDetail(String offerDetailId, OffersDetailDto offersDetailDto) {
        LOGGER.info("updateOfferDetail(id={})", offersDetailDto.getOfferDetailCode());

        Optional<OffersDetail> offersDetail = offerDetailRepository.findById(UUID.fromString(offerDetailId));

        if (offersDetail.isEmpty()) {
            LOGGER.error("offer detail with code {} is not present", offersDetailDto.getOfferDetailCode());
            throw new ResourceNotFoundException("Offer detail not found");
        }

        OffersDetail offersDetailToUpdate = offerDetailDtoToOfferDetailMapper.map(offersDetailDto).orElseThrow(
                () -> new ModelMappingException("offerDetailDtoToOfferDetailMapper was unable to convert the model")
        );
        offersDetailToUpdate.setOfferDetailId(UUID.fromString(offerDetailId));
        offerDetailRepository.save(offersDetailToUpdate);
    }

    @Override
    public OfferDetailResponseDto getOfferDetail(String offerDetailId) {
        LOGGER.info("getOfferDetail(id={})}", offerDetailId);

        OffersDetail offersDetail = findAndThrowExceptionIfNotExists(offerDetailId);
        return offerDetailToOfferDetailDtoMapper.map(offersDetail).orElseThrow(
                () -> new ModelMappingException("offerDetailDtoToOfferDetailMapper was unable to convert the model")
        );
    }

    private OffersDetail findAndThrowExceptionIfNotExists(String offerDetailId) {
        Optional<OffersDetail> offersDetail = offerDetailRepository.findById(UUID.fromString(offerDetailId));

        if (offersDetail.isEmpty()) {
            LOGGER.error("Offer detail not found with id {}", offerDetailId);
            throw new ResourceNotFoundException("Offer detail not found");
        }

        return offersDetail.get();
    }
}
