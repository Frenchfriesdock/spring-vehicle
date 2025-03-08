package com.hosiky.common.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    public static final Integer SUCCESS = 200;

    public static final Integer ERROR = 500;

    private Integer code;

    private String msg;

    private T data;

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.setCode(SUCCESS);
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = SUCCESS;
        return result;
    }

    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> result = new Result<T>();
        result.code = code;
        result.msg = msg;
        return result;
    }

    public static <T> Result<T> fromServiceResult(ServiceResult<T> serviceResult) {
        Result<T> result = new Result<>();
        result.setCode(serviceResult.getCode());
        result.setMsg(serviceResult.getMsg());
        result.setData(serviceResult.getData());
        return result;
    }

}
