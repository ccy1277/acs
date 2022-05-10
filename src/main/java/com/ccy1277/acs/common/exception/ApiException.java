package com.ccy1277.acs.common.exception;

import com.ccy1277.acs.common.result.IResultCode;

/**
 * 自定义api异常
 * created by ccy on 2022/5/9
 */
public class ApiException extends RuntimeException{
    private static final long serialVersionUID = 3969754996774117551L;
    private IResultCode code;

    public ApiException(){}

    public ApiException(String msg){
        super(msg);
    }

    public ApiException(IResultCode code){
        super(code.getMessage());
        this.code = code;
    }

    public ApiException(Throwable cause){
        super(cause);
    }

    public ApiException(String msg, Throwable cause){
        super(msg, cause);
    }

    public IResultCode getResultCode() {
        return code;
    }
}
