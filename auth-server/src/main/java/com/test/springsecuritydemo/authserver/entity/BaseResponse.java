package com.test.springsecuritydemo.authserver.entity;

import lombok.Data;

/**
 * 通用响应类
 *
 * @param <T>
 */
@Data
public class BaseResponse<T> {

    private int code;

    private String message;

    private T data;

    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<T>(200, "success", null);
    }

    public static <T> BaseResponse<T> success(int code, String message, T data) {
        return new BaseResponse<T>(code, message, data);
    }

    public static <T> BaseResponse<T> error() {
        return new BaseResponse<T>(200, "success", null);
    }

    public static <T> BaseResponse<T> error(int code, String message, T data) {
        return new BaseResponse<T>(code, message, data);
    }

}
