package com.shareit.shareit.domain;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {

    SUCCESS(200, "GEN-000", HttpStatus.OK, "Success"),

    // Member error : MEM
    MEMBER_NOT_FOUND(400, "MEM-001", HttpStatus.BAD_REQUEST, "member not found"),

    // Post error : PST
    POST_NOT_FOUND(400, "PST-001", HttpStatus.BAD_REQUEST, "Post not found"),
    POST_UNAUTHORIZED(401, "PST-001", HttpStatus.UNAUTHORIZED, "cannot access to this post"),
    POST_SAVE_ERROR(500, "PST-010", HttpStatus.INTERNAL_SERVER_ERROR, "post save error"),

    // Like error : LKE
    LIKE_EXIST(400, "LKE-001", HttpStatus.BAD_REQUEST, "Like already exist")
    ;

    private final Integer status;
    private final String code;
    private final HttpStatus httpStatus;
    private final String message;


    ResponseCode(Integer status, String code, HttpStatus httpStatus, String message) {
        this.status = status;
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
