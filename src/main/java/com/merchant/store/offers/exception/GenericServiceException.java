package com.merchant.store.offers.exception;

import com.merchant.store.offers.controller.error.ErrorCodesEnum;

public class GenericServiceException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ErrorCodesEnum errorCode = ErrorCodesEnum.GENERIC_ERROR;

    public GenericServiceException() {
        super();
    }

    public GenericServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericServiceException(String message) {
        super(message);
    }

    public GenericServiceException errorCode(ErrorCodesEnum errorCode) {
        this.errorCode = errorCode;
        return this;
    }
    
    public GenericServiceException errorCode(ErrorCodesEnum errorCode, String message) {
        this.errorCode = errorCode;
        return this;
    }

    public ErrorCodesEnum getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCodesEnum errorCode) {
        this.errorCode = errorCode;
    }
}
