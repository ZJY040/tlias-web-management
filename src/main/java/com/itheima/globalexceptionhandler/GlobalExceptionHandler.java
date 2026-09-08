package com.itheima.globalexceptionhandler;

import com.itheima.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result ex(Exception e){e.printStackTrace();
        return Result.error("对不起,操作失败,请联系管理员");
    }
}
