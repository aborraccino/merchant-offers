package com.merchant.store.offers.controller.error;

public enum ErrorCodesEnum {

    GENERIC_ERROR("99", "Generic Internal Error"),
    NOT_FOUND_ERROR("01", "Resource Not Found"),
    DUPLICATED_ERROR("02", "Already Exists"),
    MODEL_MAPPING_EXCEPTION("03", "Model Mapping Error"),
    OFFER_EXPIRED("04", "Offer is expired"),
    CONSTRAINT_ERROR("05", "Constraint Violation"),
    VALIDATION_ERROR("06", "Malformed Input Data");

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
