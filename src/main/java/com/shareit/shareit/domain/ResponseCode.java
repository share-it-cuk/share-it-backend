package com.shareit.shareit.domain;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {

    SUCCESS(200, "GEN-000", HttpStatus.OK);

    private final Integer status;
    private final String code;
    private final HttpStatus httpStatus;


    ResponseCode(Integer status, String code, HttpStatus httpStatus) {
        this.status = status;
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
