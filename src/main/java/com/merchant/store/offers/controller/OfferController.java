package com.merchant.store.offers.controller;

import com.merchant.store.offers.dto.OfferUpdateRequestDto;
import com.merchant.store.offers.dto.OfferCreateRequestDto;
import com.merchant.store.offers.dto.OfferResponseDto;
import com.merchant.store.offers.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/merchant/store/v1/api/")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @PostMapping("/offers")
    public ResponseEntity<OfferResponseDto> createOffer(@Valid @RequestBody OfferCreateRequestDto offerDto) {
        UUID offerId = offerService.createOffer(offerDto);
        return new ResponseEntity<>(new OfferResponseDto(offerId), HttpStatus.CREATED);
    }

    @PutMapping("/offers/{offerId}")
    public ResponseEntity<HttpStatus> updateOfferById(@Valid @RequestBody OfferUpdateRequestDto offerUpdateDto) {
        offerService.upateOffer(offerUpdateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/offers/{offerId}")
    public ResponseEntity<OfferCreateRequestDto> getOfferById(@PathVariable("offerId") long id) {
        OfferCreateRequestDto offerDTO = offerService.getOfferById(id);
        return new ResponseEntity<>(offerDTO, HttpStatus.OK);
    }

    @DeleteMapping("/offers/{offerId}")
    public ResponseEntity<HttpStatus> expireOfferById(@PathVariable("id") long offerId) {
        offerService.expireOfferById(offerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
