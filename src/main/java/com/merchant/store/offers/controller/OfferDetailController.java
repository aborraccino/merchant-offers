package com.merchant.store.offers.controller;

import com.merchant.store.offers.dto.OfferDetailResponseDto;
import com.merchant.store.offers.dto.OffersDetailCreateRequestDto;
import com.merchant.store.offers.dto.OffersDetailUpdateRequestDto;
import com.merchant.store.offers.service.OfferDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/merchant/store/v1/api/offers/{offerId}")
public class OfferDetailController {

    @Autowired
    private OfferDetailService offerDetailService;

    @PostMapping("/details")
    public ResponseEntity<HttpStatus> addDetail(@PathVariable("offerId") String offerId,
                                                @Valid @RequestBody OffersDetailCreateRequestDto offersDetailCreateRequestDto) {
        offerDetailService.addOfferDetail(offerId, offersDetailCreateRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/details")
    public ResponseEntity<HttpStatus> updateDetail(@PathVariable("offerId") String offerId,
                                                   @Valid @RequestBody
                                                           OffersDetailUpdateRequestDto offersDetailUpdateRequestDto) {
        offerDetailService.updateOfferDetail(offersDetailUpdateRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/details/{offerDetailId}")
    public ResponseEntity<OfferDetailResponseDto> getDetail(@PathVariable("offerId") String offerId,
                                                            @PathVariable("offerDetailId") String offerDetailId) {
        OfferDetailResponseDto
                offersDetailResponseDto = offerDetailService.getOfferDetail(offerId, offerDetailId);
        return new ResponseEntity<>(offersDetailResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/details/{offerDetailId}")
    public ResponseEntity<OfferDetailResponseDto> deleteDetail(@PathVariable("offerId") String offerId,
                                                            @PathVariable("offerDetailId") String offerDetailId) {
        offerDetailService.removeOfferDetail(offerId, offerDetailId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
