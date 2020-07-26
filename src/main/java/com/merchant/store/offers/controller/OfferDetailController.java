package com.merchant.store.offers.controller;

import com.merchant.store.offers.dto.OfferDetailResponseDto;
import com.merchant.store.offers.dto.OffersDetailDto;
import com.merchant.store.offers.service.adapter.OfferDetailServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/merchant/store/v1/api/offers/{offerId}")
public class OfferDetailController {

    @Autowired
    private OfferDetailServiceAdapter offerDetailServiceAdapter;

    @PostMapping("/details")
    public ResponseEntity<UUID> addDetail(@PathVariable("offerId") String offerId,
                                          @Valid @RequestBody OffersDetailDto offersDetailCreateRequestDto) {
        final UUID uuid = offerDetailServiceAdapter.addOfferDetail(offerId, offersDetailCreateRequestDto);
        return new ResponseEntity<>(uuid, HttpStatus.CREATED);
    }

    @PutMapping("/details/{offerDetailId}")
    public ResponseEntity<HttpStatus> updateDetail(@PathVariable("offerId") String offerId,
                                                   @PathVariable("offerDetailId") String offerDetailId,
                                                   @Valid @RequestBody
                                                           OffersDetailDto offersDetailDto) {
        offerDetailServiceAdapter.updateOfferDetail(offerId, offerDetailId, offersDetailDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/details/{offerDetailId}")
    public ResponseEntity<OfferDetailResponseDto> getDetail(@PathVariable("offerId") String offerId,
                                                            @PathVariable("offerDetailId") String offerDetailId) {
        OfferDetailResponseDto
                offersDetailResponseDto = offerDetailServiceAdapter.getOfferDetail(offerId, offerDetailId);
        return new ResponseEntity<>(offersDetailResponseDto, HttpStatus.OK);
    }

}
