package com.shareit.shareit.Exceptions;

import com.shareit.shareit.domain.ResponseCode;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

	private ResponseCode responseCode;

	public BusinessException() {
		super();
	}

	public BusinessException(ResponseCode responseCode) {
		super(responseCode.getMessage());
		this.responseCode = responseCode;
	}

}
