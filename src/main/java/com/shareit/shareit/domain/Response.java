package com.shareit.shareit.domain;

import lombok.*;

import org.springframework.http.HttpStatus;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

	private Integer status;
	private List<String> code;
	private T data;
	private List<String> message;

	public static Response<Void> ok() {
		Response<Void> response = new Response<>();
		response.status = HttpStatus.OK.value();
		response.code = List.of(ResponseCode.SUCCESS.getCode());

		return response;
	}

	public static <T> Response<T> ok(T data) {
		Response<T> response = new Response<>();
		response.status = HttpStatus.OK.value();
		response.code = List.of(ResponseCode.SUCCESS.getCode());
		response.data = data;

		return response;
	}

}
