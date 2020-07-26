package com.merchant.store.offers.controller;

import com.merchant.store.offers.dto.OfferDto;
import com.merchant.store.offers.dto.OfferResponseDto;
import com.merchant.store.offers.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/merchant/store/v1/api/")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @PostMapping("/offers")
    public ResponseEntity<OfferResponseDto> createOffer(@Valid @RequestBody OfferDto offerDto) {
        OfferResponseDto offer = offerService.createOffer(offerDto);
        return new ResponseEntity<>(offer, HttpStatus.CREATED);
    }

    @PutMapping("/offers/{offerId}")
    public ResponseEntity<HttpStatus> updateOfferById(@PathVariable("offerId") String offerId,
                                                      @Valid @RequestBody OfferDto offerDto) {
        offerService.updateOffer(offerDto, offerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/offers/{offerId}")
    public ResponseEntity<OfferResponseDto> getOfferById(@PathVariable("offerId") String id) {
        OfferResponseDto offerDTO = offerService.getOfferById(id);
        return new ResponseEntity<>(offerDTO, HttpStatus.OK);
    }

    @DeleteMapping("/offers/{offerId}")
    public ResponseEntity<HttpStatus> expireOfferById(@PathVariable("offerId") String offerId) {
        offerService.expireOfferById(offerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
