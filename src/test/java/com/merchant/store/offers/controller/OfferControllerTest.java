package com.merchant.store.offers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.merchant.store.offers.dto.CurrencyEnumDto;
import com.merchant.store.offers.dto.OfferDto;
import com.merchant.store.offers.dto.OfferResponseDto;
import com.merchant.store.offers.dto.OffersDetailDto;
import com.merchant.store.offers.repository.DummyFactory;
import com.merchant.store.offers.service.OfferService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Test
    @DisplayName("When offer exists, then return the offer (200)")
    public void testGetOfferWhenItExists() throws Exception {
        // given
        long offerId = 1L;
        OfferResponseDto offerResponseDto = DummyFactory.givenDummyValidOfferResponseDtoNoDetails(LocalDateTime.now());
        given(offerService.getOfferById(any(String.class))).willReturn(offerResponseDto);

        // when-then
        mockMvc.perform(get(buildUrlWithIdVariable(OFFERS_PATH), offerId)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.offerId", is(offerResponseDto.getOfferId().toString())))
                .andExpect(jsonPath("$.offerCode", is(offerResponseDto.getOfferCode())))
                .andExpect(jsonPath("$.description", is(offerResponseDto.getDescription())))
                .andExpect(jsonPath("$.price", is(offerResponseDto.getPrice())))
                .andExpect(jsonPath("$.currency", is(String.valueOf(offerResponseDto.getCurrency()))))
                .andExpect(jsonPath("$.expired", is(offerResponseDto.isExpired())))
                .andExpect(jsonPath("$.offerStartDate", is(offerResponseDto.getOfferStartDate().format(
                        DateTimeFormatter.ofPattern(DATE_FORMAT)))))
                .andExpect(jsonPath("$.offerExpireDate").doesNotExist())
                .andExpect(jsonPath("$.offersDetail").doesNotExist());
    }

    @Test
    @DisplayName("When offer exists with correlated detail, then return the offer with its child (200)")
    public void testGetOfferWhenItExistsWithDetail() throws Exception {
        // given
        OfferResponseDto offerResponseDto = DummyFactory.givenDummyValidOfferResponseDto(LocalDateTime.now());
        offerResponseDto.setOffersDetail(List.of(new OffersDetailDto("code", "description", 5)));
        given(offerService.getOfferById(any(String.class))).willReturn(offerResponseDto);

        // when-then
        mockMvc.perform(get(buildUrlWithIdVariable(OFFERS_PATH), offerResponseDto.getOfferId())
                                .contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.offerId", is(offerResponseDto.getOfferId().toString())))
                .andExpect(jsonPath("$.offerCode", is(offerResponseDto.getOfferCode())))
                .andExpect(jsonPath("$.description", is(offerResponseDto.getDescription())))
                .andExpect(jsonPath("$.price", is(offerResponseDto.getPrice())))
                .andExpect(jsonPath("$.currency", is(String.valueOf(offerResponseDto.getCurrency()))))
                .andExpect(jsonPath("$.expired", is(offerResponseDto.isExpired())))
                .andExpect(jsonPath("$.offerStartDate", is(offerResponseDto.getOfferStartDate().format(
                        DateTimeFormatter.ofPattern(DATE_FORMAT)))))
                .andExpect(jsonPath("$.offerExpireDate").doesNotExist())
                .andExpect(jsonPath("$.offersDetail[0].offerDetailCode", is(offerResponseDto.getOffersDetail().get(0).getOfferDetailCode())))
                .andExpect(jsonPath("$.offersDetail[0].offerDetailDescription", is(offerResponseDto.getOffersDetail().get(0).getOfferDetailDescription())))
                .andExpect(jsonPath("$.offersDetail[0].quantity", is(offerResponseDto.getOffersDetail().get(0).getQuantity())));
    }

    @Test
    @DisplayName("When offer is valid, then create an offer and return a successful response (201)")
    public void testCreateOfferWhenItIsValid() throws Exception {
        // given
        OfferDto offerDto = givenDummyValidOfferDto();
        UUID offerId = UUID.randomUUID();
        given(offerService.createOffer(any(OfferDto.class))).willReturn(new OfferResponseDto(offerId));

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
        OfferDto invalidOfferDTO = givenDummyInvalidOfferDtoWithoutCode();
        UUID offerId = UUID.randomUUID();
        given(offerService.createOffer(any(OfferDto.class))).willReturn(new OfferResponseDto(offerId));

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
        OfferDto invalidOfferDTO = givenDummyInvalidOfferDtoWithoutPrice();
        UUID offerId = UUID.randomUUID();
        given(offerService.createOffer(any(OfferDto.class))).willReturn(new OfferResponseDto(offerId));

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
        OfferDto invalidOfferDTO = givenDummyInvalidOfferDtoWithoutCurrency();
        UUID offerId = UUID.randomUUID();
        given(offerService.createOffer(any(OfferDto.class))).willReturn(new OfferResponseDto(offerId));

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
        OfferDto offerDto = givenDummyValidOfferDto();
        String offerToUpdate = "1234";
        OfferResponseDto dummyResponseDto = new OfferResponseDto(UUID.randomUUID());
        given(offerService.updateOffer(any(OfferDto.class), any(String.class))).willReturn(dummyResponseDto);

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
        String offerToDeleteId = "12345";
        doNothing().when(offerService).expireOfferById(offerToDeleteId);

        // when-then
        mockMvc.perform(get(buildUrlWithIdVariable(OFFERS_PATH), offerToDeleteId)
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private OfferDto givenDummyInvalidOfferDtoWithoutCurrency() {
        return new OfferDto()
                .setOfferCode("myCode")
                .setPrice(10d);
    }

    private OfferDto givenDummyInvalidOfferDtoWithoutPrice() {
        return new OfferDto()
                .setOfferCode("myCode")
                .setCurrency(CurrencyEnumDto.GBP);
    }

    private OfferDto givenDummyInvalidOfferDtoWithoutCode() {
        return new OfferDto()
                .setCurrency(CurrencyEnumDto.GBP)
                .setPrice(10d);
    }

    private OfferDto givenDummyValidOfferDto(){
        return new OfferDto()
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
