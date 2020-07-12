package com.merchant.store.offers.exception;

import com.merchant.store.offers.controller.error.ErrorCodesEnum;

public class ModelMappingException extends GenericServiceException {

    public ModelMappingException(String message) {
        super(message);
        errorCode(ErrorCodesEnum.MODEL_MAPPING_EXCEPTION);
    }
}
