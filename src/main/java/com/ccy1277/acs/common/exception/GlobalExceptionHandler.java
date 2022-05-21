package com.ccy1277.acs.common.exception;

import com.ccy1277.acs.common.api.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * 全局异常处理类
 * created by ccy on 2022/5/9
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    // 处理ApiException
    @ExceptionHandler(value = ApiException.class)
    @ResponseBody
    public CommonResult handleApiException(ApiException e) {
        if (e.getResultCode() != null) {
            return CommonResult.failed(e.getResultCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    // 处理参数校验失败抛出的MethodArgumentNotValidException或者ConstraintViolationException异常
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<String> list = new ArrayList<>();
        // 从异常对象中拿到ObjectError对象
        if (!e.getBindingResult().getAllErrors().isEmpty()){
            for(ObjectError error:e.getBindingResult().getAllErrors()){
                list.add(e.getParameter().toString() + error.getDefaultMessage());
            }
        }
        return CommonResult.validateFailed(list.toString());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResult handleConstraintViolationException(ConstraintViolationException e){
        return CommonResult.validateFailed(e.getMessage());
    }
}
