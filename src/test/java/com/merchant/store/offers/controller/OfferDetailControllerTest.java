package com.merchant.store.offers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.merchant.store.offers.dto.OfferDetailResponseDto;
import com.merchant.store.offers.dto.OffersDetailUpdateRequestDto;
import com.merchant.store.offers.service.OfferDetailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = OfferDetailController.class)
public class OfferDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private OfferDetailService offerDetailService;

    private static final String OFFERS_PATH = "/merchant/store/v1/api/offers";

    @Test
    @DisplayName("When offer detail exists, then it returns the offer detail (200)")
    public void testGetWhenOfferDetailExists() throws Exception {
        // given
        UUID offerId = UUID.randomUUID();
        UUID offerDetailId = UUID.randomUUID();
        OfferDetailResponseDto offersDetailResponseDto = givenDummyOfferDetailResponseDto(offerId, offerDetailId);
        given(offerDetailService.getOfferDetail(any(String.class), any(String.class))).willReturn(offersDetailResponseDto);

        // when-then
        mockMvc.perform(get(buildUrlWithIdVariable(OFFERS_PATH), offerId, offerDetailId)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.offerDetailId", is(offersDetailResponseDto.getOfferDetailId().toString())))
                .andExpect(jsonPath("$.offerId", is(offersDetailResponseDto.getOfferId().toString())))
                .andExpect(jsonPath("$.offerDetailCode", is(offersDetailResponseDto.getOfferDetailCode())))
                .andExpect(jsonPath("$.offerDetailDescription", is(offersDetailResponseDto.getOfferDetailDescription())))
                .andExpect(jsonPath("$.quantity", is(offersDetailResponseDto.getQuantity())));
    }

    @Test
    @DisplayName("When offer detail or the main offer does not exists, then it returns not found error (404)")
    public void testGetWhenOfferOrDetailDoesNotExists() throws Exception {
        // given
        UUID offerId = UUID.randomUUID();
        UUID offerDetailId = UUID.randomUUID();
        given(offerDetailService.getOfferDetail(any(String.class), any(String.class))).willThrow(new EntityNotFoundException("Resource not Found"));

        // when-then
        mockMvc.perform(get(buildUrlWithIdVariable(OFFERS_PATH), offerId, offerDetailId)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("When offer detail is valid, then it returns success (201)")
    public void testPostWhenDetailIsValid() throws Exception {
        // given
        UUID offerId = UUID.randomUUID();
        OffersDetailUpdateRequestDto offersDetailUpdateRequestDto = givenDummyOfferDetailCreateRequest();
        doNothing().when(offerDetailService).upateOfferDetail(any(OffersDetailUpdateRequestDto.class));

        // when-then
        mockMvc.perform(post(OFFERS_PATH + "/{offerID}/details", offerId)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(offersDetailUpdateRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("When offer detail is not valid because quantity is equal to 0, then it returns bad request (400)")
    public void testPostWhenDetailQuantityIsNotValid() throws Exception {
        // given
        UUID offerId = UUID.randomUUID();
        OffersDetailUpdateRequestDto offersDetailUpdateRequestDto = givenDummyInvalidOfferDetailCreateRequest();
        doNothing().when(offerDetailService).upateOfferDetail(any(OffersDetailUpdateRequestDto.class));

        // when-then
        mockMvc.perform(post(OFFERS_PATH + "/{offerID}/details", offerId)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(offersDetailUpdateRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When offer detail is valid, then delete detail and returns success code (204)")
    public void testDeleteWhenDetailIsValid() throws Exception {
        // given
        String offerId = "1";
        String offerDetailId = "1";
        doNothing().when(offerDetailService).removeOfferDetail(offerId, offerDetailId);

        // when-then
        mockMvc.perform(delete(buildUrlWithIdVariable(OFFERS_PATH), offerId, offerDetailId)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private OffersDetailUpdateRequestDto givenDummyOfferDetailCreateRequest() {
        return new OffersDetailUpdateRequestDto()
                .setOfferDetailCode("offer detail code")
                .setOfferDetailDescription("offer detail description")
                .setQuantity(10);
    }

    private OffersDetailUpdateRequestDto givenDummyInvalidOfferDetailCreateRequest() {
        return new OffersDetailUpdateRequestDto()
                .setQuantity(0);
    }

    private String buildUrlWithIdVariable(String offersPath) {
        return new StringBuilder()
                .append(offersPath)
                .append("/{offerId}")
                .append("/details")
                .append("/{offerDetailId}")
                .toString();
    }

    private OfferDetailResponseDto givenDummyOfferDetailResponseDto(UUID offerId, UUID offerDetailId) {
        OfferDetailResponseDto offerDetailResponseDto = new OfferDetailResponseDto(offerDetailId, offerId);

        offerDetailResponseDto
                .setOfferDetailCode("detail code")
                .setOfferDetailDescription("detail description")
                .setQuantity(2);
        return offerDetailResponseDto;
    }

}
