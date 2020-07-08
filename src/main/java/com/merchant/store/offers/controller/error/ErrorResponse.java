package com.merchant.store.offers.controller.error;

public class ErrorResponse {

    private String code;

    private String message;

    public ErrorResponse() {
    }

    public String getCode() {
        return code;
    }

    public ErrorResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErrorResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
