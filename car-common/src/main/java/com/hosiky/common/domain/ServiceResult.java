package com.hosiky.common.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceResult <T> implements Serializable {

    public static final Integer SUCCESS = 200;

    public static final Integer ERROR = 500;

    private Integer code;

    private String msg;

    private T data;

    public static <T> ServiceResult<T> success(T object) {
        ServiceResult<T> result = new ServiceResult<T>();
        result.setCode(SUCCESS);
        result.setData(object);
        return result;
    }

    public static <T> ServiceResult<T> success() {
        ServiceResult<T> result = new ServiceResult<T>();
        result.setCode(SUCCESS);
        return result;
    }

    public static <T> ServiceResult<T> error(Integer code, String msg) {
        ServiceResult<T> result = new ServiceResult<T>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
