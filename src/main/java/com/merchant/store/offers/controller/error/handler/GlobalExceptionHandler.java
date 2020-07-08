package com.merchant.store.offers.controller.error.handler;

import com.merchant.store.offers.controller.error.ErrorCodesEnum;
import com.merchant.store.offers.controller.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFound(EntityNotFoundException ex) {
        return buildError(ex, ErrorCodesEnum.NOT_FOUND_ERROR, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<ErrorResponse> buildError(Exception ex, ErrorCodesEnum error, HttpStatus httpStatus) {
        ErrorResponse response = new ErrorResponse()
                .setCode(error.getCode())
                .setMessage(ex.getMessage());
        return new ResponseEntity<>(response, httpStatus);
    }

}
