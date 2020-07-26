package com.merchant.store.offers.controller.error.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.merchant.store.offers.controller.error.ErrorCodesEnum;
import com.merchant.store.offers.controller.error.ErrorResponse;
import com.merchant.store.offers.exception.DuplicateResourceException;
import com.merchant.store.offers.exception.GenericServiceException;
import com.merchant.store.offers.exception.OfferExpiredException;
import com.merchant.store.offers.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.UnexpectedTypeException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFound(ResourceNotFoundException ex) {
        return buildError(ex, ErrorCodesEnum.NOT_FOUND_ERROR, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> offerDuplicated(DuplicateResourceException ex) {
        return buildError(ex, ErrorCodesEnum.DUPLICATED_ERROR, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(OfferExpiredException.class)
    public ResponseEntity<ErrorResponse> offerExpired(OfferExpiredException ex) {
        return buildError(ex, ErrorCodesEnum.OFFER_EXPIRED, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GenericServiceException.class)
    public ResponseEntity<ErrorResponse> genericError(GenericServiceException ex) {
        return buildError(ex, ErrorCodesEnum.GENERIC_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> constraintViolation(DataIntegrityViolationException ex) {
        return buildError(ex, ErrorCodesEnum.CONSTRAINT_ERROR, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ErrorResponse> requestWithUnexpectedType(UnexpectedTypeException ex) {
        return buildError(ex, ErrorCodesEnum.VALIDATION_ERROR, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> requestWithInvalidArgument(MethodArgumentNotValidException ex) {
        return buildError(ex, ErrorCodesEnum.VALIDATION_ERROR, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> requestNotReadable(HttpMessageNotReadableException ex) {
        return buildError(ex, ErrorCodesEnum.VALIDATION_ERROR, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ErrorResponse> requestNotParsable(JsonParseException ex) {
        return buildError(ex, ErrorCodesEnum.VALIDATION_ERROR, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArguments(IllegalArgumentException ex) {
        return buildError(ex, ErrorCodesEnum.VALIDATION_ERROR, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ErrorResponse> buildError(Exception ex, ErrorCodesEnum error, HttpStatus httpStatus) {
        ErrorResponse response = new ErrorResponse()
                .setCode(error.getCode())
                .setMessage(ex.getMessage());
        return new ResponseEntity<>(response, httpStatus);
    }

}
