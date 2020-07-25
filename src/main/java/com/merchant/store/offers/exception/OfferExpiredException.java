package com.merchant.store.offers.exception;

import com.merchant.store.offers.controller.error.ErrorCodesEnum;

public class OfferExpiredException extends GenericServiceException {

	/**
	 * 
	 */
	public OfferExpiredException(String message) {
		super(message);
		errorCode(ErrorCodesEnum.NOT_FOUND_ERROR);
	}
}
