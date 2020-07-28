package com.merchant.store.offers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.merchant.store.offers.dto.OfferDetailResponseDto;
import com.merchant.store.offers.dto.OffersDetailDto;
import com.merchant.store.offers.exception.ResourceNotFoundException;
import com.merchant.store.offers.repository.DummyFactory;
import com.merchant.store.offers.service.adapter.OfferDetailServiceAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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
    private OfferDetailServiceAdapter offerDetailService;

    private static final String OFFERS_PATH = "/merchant/store/v1/api/offers";

    private static final UUID OFFER_ID = UUID.fromString("3db46740-71d3-4881-8a0e-7206f9e1c089");
    private static final UUID OFFER_DETAIL_ID = UUID.fromString("177d72da-2996-40bb-9396-ddf13c40c978");

    @Test
    @DisplayName("When offer detail exists, then it returns the offer detail (200)")
    public void testGetWhenOfferDetailExists() throws Exception {
        // given
        OfferDetailResponseDto offersDetailResponseDto = givenDummyOfferDetailResponseDto(OFFER_ID, OFFER_DETAIL_ID);
        given(offerDetailService.getOfferDetail(anyString(), anyString())).willReturn(offersDetailResponseDto);

        // when-then
        mockMvc.perform(get(buildUrlWithIdVariable(OFFERS_PATH), OFFER_ID.toString(), OFFER_DETAIL_ID.toString())
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.offerDetailId", is(offersDetailResponseDto.getOfferDetailId().toString())))
                .andExpect(jsonPath("$.offerDetailCode", is(offersDetailResponseDto.getOfferDetailCode())))
                .andExpect(jsonPath("$.offerDetailDescription", is(offersDetailResponseDto.getOfferDetailDescription())))
                .andExpect(jsonPath("$.quantity", is(offersDetailResponseDto.getQuantity())));
    }

    @Test
    @DisplayName("When offer detail or the main offer does not exists, then it returns not found error (404)")
    public void testGetWhenOfferOrDetailDoesNotExists() throws Exception {
        // given
        given(offerDetailService.getOfferDetail(anyString(), anyString())).willThrow(new ResourceNotFoundException("Resource not Found"));

        // when-then
        mockMvc.perform(get(buildUrlWithIdVariable(OFFERS_PATH), OFFER_ID, OFFER_DETAIL_ID)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("When offer detail is valid, then it returns success (201)")
    public void testPostWhenDetailIsValid() throws Exception {
        // given
        OffersDetailDto offersDetailDto = DummyFactory.givenDummyOfferDetailDto();
        doNothing().when(offerDetailService).updateOfferDetail(anyString(), anyString(), any(OffersDetailDto.class));

        // when-then
        mockMvc.perform(post(OFFERS_PATH + "/{offerID}/details", OFFER_ID, OFFER_DETAIL_ID)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(offersDetailDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("When offer detail is not valid because quantity is equal to 0, then it returns bad request (400)")
    public void testPostWhenDetailQuantityIsNotValid() throws Exception {
        // given
        OffersDetailDto offersDetailUpdateRequestDto = DummyFactory.givenDummyInvalidQuantityOfferDetailDto();
        doNothing().when(offerDetailService).updateOfferDetail(anyString(), anyString(), any(OffersDetailDto.class));

        // when-then
        mockMvc.perform(post(OFFERS_PATH + "/{offerID}/details", OFFER_ID, OFFER_DETAIL_ID)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(offersDetailUpdateRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When offer detail is not valid because the code is null, then it returns bad request (400)")
    public void testPostWhenCodeIsNotValid() throws Exception {
        // given
        OffersDetailDto offersDetailUpdateRequestDto = DummyFactory.givenDummyInvalidCodeOfferDetailDto();
        doNothing().when(offerDetailService).updateOfferDetail(anyString(), anyString(), any(OffersDetailDto.class));

        // when-then
        mockMvc.perform(post(OFFERS_PATH + "/{offerID}/details", OFFER_ID, OFFER_DETAIL_ID)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(offersDetailUpdateRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When offer detail is not valid because the description is empty, then it returns bad request (400)")
    public void testPostWhenDescriptionIsEmpty() throws Exception {
        // given
        OffersDetailDto offersDetailUpdateRequestDto = DummyFactory.givenDummyInvalidDescriptionOfferDetailDto();
        doNothing().when(offerDetailService).updateOfferDetail(anyString(), anyString(), any(OffersDetailDto.class));

        // when-then
        mockMvc.perform(post(OFFERS_PATH + "/{offerID}/details", OFFER_ID, OFFER_DETAIL_ID)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(offersDetailUpdateRequestDto)))
                .andExpect(status().isBadRequest());
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
        OfferDetailResponseDto offerDetailResponseDto = new OfferDetailResponseDto(offerDetailId);

        offerDetailResponseDto
                .setOfferDetailCode("detail code")
                .setOfferDetailDescription("detail description")
                .setQuantity(2);
        return offerDetailResponseDto;
    }

}
