package com.merchant.store.offers.exception;

import com.merchant.store.offers.controller.error.ErrorCodesEnum;

public class DuplicateResourceException extends GenericServiceException {

	private static final long serialVersionUID = 1L;

	public DuplicateResourceException(String message) {
		super(message);
		errorCode(ErrorCodesEnum.DUPLICATED_ERROR);
	}
}
