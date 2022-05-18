package com.ccy1277.acs.common.exception;

import com.ccy1277.acs.common.api.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 * created by ccy on 2022/5/9
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    // 处理ApiException
    @ExceptionHandler(value = ApiException.class)
    @ResponseBody
    public CommonResult handle(ApiException e) {
        if (e.getResultCode() != null) {
            return CommonResult.failed(e.getResultCode());
        }
        return CommonResult.failed(e.getMessage());
    }
}
