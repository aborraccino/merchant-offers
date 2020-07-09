package com.merchant.store.offers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.merchant.store.offers.dto.CurrencyEnumDto;
import com.merchant.store.offers.dto.OfferUpdateRequestDto;
import com.merchant.store.offers.dto.OfferCreateRequestDto;
import com.merchant.store.offers.dto.OffersDetailCreateRequestDto;
import com.merchant.store.offers.service.OfferService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = OfferController.class)
public class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private OfferService offerService;

    private static final String OFFERS_PATH = "/merchant/store/v1/api/offers";

    @Test
    @DisplayName("When offer exists, then return the offer (200)")
    public void testGetOfferWhenItExists() throws Exception {
        // given
        long offerId = 1L;
        OfferCreateRequestDto offerCreateDto = givenDummyValidOfferDto();
        given(offerService.getOfferById(any(Long.class))).willReturn(offerCreateDto);

        // when-then
        mockMvc.perform(get(buildUrlWithIdVariable(OFFERS_PATH), offerId)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.offerCode", is(offerCreateDto.getOfferCode())))
                .andExpect(jsonPath("$.description", is(offerCreateDto.getDescription())))
                .andExpect(jsonPath("$.price", is(offerCreateDto.getPrice())))
                .andExpect(jsonPath("$.currency", is(String.valueOf(offerCreateDto.getCurrency()))))
                .andExpect(jsonPath("$.expirationDelay", is(offerCreateDto.getExpirationDelay())));
    }

    @Test
    @DisplayName("When offer exists with correlated detail, then return the offer with its child (200)")
    public void testGetOfferWhenItExistsWithDetail() throws Exception {
        // given
        long offerId = 1L;
        OfferCreateRequestDto offerCreateDto = givenDummyValidOfferDto();
        offerCreateDto.setOffersDetail(List.of(new OffersDetailCreateRequestDto("code", "description", 5)));
        given(offerService.getOfferById(any(Long.class))).willReturn(offerCreateDto);

        // when-then
        mockMvc.perform(get(buildUrlWithIdVariable(OFFERS_PATH), offerId)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.offerCode", is(offerCreateDto.getOfferCode())))
                .andExpect(jsonPath("$.description", is(offerCreateDto.getDescription())))
                .andExpect(jsonPath("$.price", is(offerCreateDto.getPrice())))
                .andExpect(jsonPath("$.currency", is(String.valueOf(offerCreateDto.getCurrency()))))
                .andExpect(jsonPath("$.expirationDelay", is(offerCreateDto.getExpirationDelay())))
                .andExpect(jsonPath("$.offersDetail[0].offerDetailCode", is(offerCreateDto.getOffersDetail().get(0).getOfferDetailCode())))
                .andExpect(jsonPath("$.offersDetail[0].offerDetailDescription", is(offerCreateDto.getOffersDetail().get(0).getOfferDetailDescription())))
                .andExpect(jsonPath("$.offersDetail[0].quantity", is(offerCreateDto.getOffersDetail().get(0).getQuantity())));
    }

    @Test
    @DisplayName("When offer is valid, then create an offer and return a successful response (201)")
    public void testCreateOfferWhenItIsValid() throws Exception {
        // given
        OfferCreateRequestDto offerDto = givenDummyValidOfferDto();
        UUID offerId = UUID.randomUUID();
        given(offerService.createOffer(any(OfferCreateRequestDto.class))).willReturn(offerId);

        // when-then
        mockMvc.perform(post(OFFERS_PATH)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(offerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.offerId", is(String.valueOf(offerId))));
    }

    @Test
    @DisplayName("When is missing code, then the offer creation fails and the api return an error code (400)")
    public void testCreateOfferWhenIsMissingOfferCode() throws Exception {
        // given
        OfferCreateRequestDto invalidOfferDTO = givenDummyInvalidOfferDtoWithoutCode();
        UUID offerId = UUID.randomUUID();
        given(offerService.createOffer(any(OfferCreateRequestDto.class))).willReturn(offerId);

        // when-then
        mockMvc.perform(post(OFFERS_PATH)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(invalidOfferDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When is missing offer price, then the offer creation fails and the api return an error code (400)")
    public void testCreateOfferWhenIsMissingPrice() throws Exception {
        // given
        OfferCreateRequestDto invalidOfferDTO = givenDummyInvalidOfferDtoWithoutPrice();
        UUID offerId = UUID.randomUUID();
        given(offerService.createOffer(any(OfferCreateRequestDto.class))).willReturn(offerId);

        // when-then
        mockMvc.perform(post(OFFERS_PATH)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(invalidOfferDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When is missing offer currency, then the offer creation fails and the api return an error code (400)")
    public void testCreateOfferWhenIsMissingCurrency() throws Exception {
        // given
        OfferCreateRequestDto invalidOfferDTO = givenDummyInvalidOfferDtoWithoutCurrency();
        UUID offerId = UUID.randomUUID();
        given(offerService.createOffer(any(OfferCreateRequestDto.class))).willReturn(offerId);

        // when-then
        mockMvc.perform(post(OFFERS_PATH)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(invalidOfferDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When offer is updated, then and return (201)")
    public void testUpdateOffer() throws Exception {
        // given
        OfferUpdateRequestDto offerDto = new OfferUpdateRequestDto().setDescription("new description");
        long offerToUpdate = 1L;
        doNothing().when(offerService).updateOffer(any(OfferUpdateRequestDto.class));

        // when-then
        mockMvc.perform(put(buildUrlWithIdVariable(OFFERS_PATH), offerToUpdate)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(offerDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When offer is expired, then delete the offer")
    public void testExpireOffer() throws Exception {
        // given
        long offerToDeleteId = 1L;
        doNothing().when(offerService).expireOfferById(offerToDeleteId);

        // when-then
        mockMvc.perform(get(buildUrlWithIdVariable(OFFERS_PATH), offerToDeleteId)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private OfferCreateRequestDto givenDummyInvalidOfferDtoWithoutCurrency() {
        return new OfferCreateRequestDto()
                .setOfferCode("myCode")
                .setPrice(10d);
    }

    private OfferCreateRequestDto givenDummyInvalidOfferDtoWithoutPrice() {
        return new OfferCreateRequestDto()
                .setOfferCode("myCode")
                .setCurrency(CurrencyEnumDto.GBP);
    }

    private OfferCreateRequestDto givenDummyInvalidOfferDtoWithoutCode() {
        return new OfferCreateRequestDto()
                .setCurrency(CurrencyEnumDto.GBP)
                .setPrice(10d);
    }

    private OfferCreateRequestDto givenDummyValidOfferDto(){
        return new OfferCreateRequestDto()
                .setOfferCode("myCode")
                .setCurrency(CurrencyEnumDto.GBP)
                .setPrice(10d)
                .setDescription("my description")
                .setExpirationDelay(10);
    }

    private String buildUrlWithIdVariable(String path) {
        return new StringBuilder()
                .append(path)
                .append("/{offerId}")
                .toString();
    }
}
