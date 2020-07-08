package com.merchant.store.offers.controller.error;

public enum ErrorCodesEnum {

    GENERIC_ERROR("99", "Generic Internal Error"),
    NOT_FOUND_ERROR("01", "Resource Not Found");

    String code;
    String defaultMessage;

    ErrorCodesEnum(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public String getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}