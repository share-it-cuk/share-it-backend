package com.shareit.shareit.domain;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	public static <T> Response<T> errorResponse(ResponseCode code) {
		Response<T> response = new Response<>();
		response.status = code.getStatus();
		response.code = List.of(code.getCode());
		response.data = null;
		response.message = List.of(code.getMessage());
		return response;
	}

	public String convertToJson() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
