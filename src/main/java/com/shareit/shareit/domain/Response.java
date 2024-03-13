package com.shareit.shareit.domain;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

    private Integer status;
    private List<String> code;
    private List<T> data;
    private List<String> message;

    public static <T>  Response<T> ok(T data){
        Response<T> response = new Response<>();
        response.status = HttpStatus.OK.value();
        response.code = List.of(ResponseCode.SUCCESS.getCode());
        response.data = List.of(data);
        return response;
    }

}
