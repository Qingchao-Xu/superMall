package org.bytes.supermallpojo.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Response<T> implements Serializable {
    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应描述
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    private Response(Integer code) {
        this.code = code;
    }

    private Response(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

    private Response(Integer code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public boolean isSuccess() {
        return Objects.equals(this.code, ResponseCode.SUCCESS.getCode());
    }

    public static <T> Response<T> success() {
        return new Response<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> Response<T> success(String message) {
        return new Response<T>(ResponseCode.SUCCESS.getCode(), message);
    }

    public static <T> Response<T> data(T data) {
        return new Response<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), data);
    }

    public static <T> Response<T> fail() {
        return new Response<T>(ResponseCode.UNKNOWN_ERROR.getCode());
    }

    public static <T> Response<T> fail(String message) {
        return new Response<T>(ResponseCode.UNKNOWN_ERROR.getCode(), message);
    }

    public static <T> Response<T> fail(Integer code, String message) {
        return new Response<T>(code, message);
    }

    public static <T> Response<T> fail(ResponseCode responseCode) {
        return new Response<T>(responseCode.getCode(), responseCode.getMsg());
    }

}
