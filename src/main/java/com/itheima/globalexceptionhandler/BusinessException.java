package com.itheima.globalexceptionhandler;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}