package com.huahua.base.controller;

import huahua.common.Result;
import huahua.common.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//切面注解 如果异常 每一次请求都会处理
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result errorHandle(Exception e){
        e.printStackTrace();
        return new Result(StatusCode.ERROR,false,e.getMessage());
    }

}
