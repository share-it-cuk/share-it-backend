package com.shareit.shareit.Exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shareit.shareit.domain.Response;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(BusinessException.class)
	public Response handleBusinessException(BusinessException ex) {
		return Response.errorResponse(ex.getResponseCode());
	}
}
