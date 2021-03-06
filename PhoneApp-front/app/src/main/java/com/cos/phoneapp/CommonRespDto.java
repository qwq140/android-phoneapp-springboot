package com.cos.phoneapp;


public class CommonRespDto<T> {
    private int code;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public CommonRespDto() {
    }

    public CommonRespDto(int code, T data) {
        this.code = code;
        this.data = data;
    }
}
