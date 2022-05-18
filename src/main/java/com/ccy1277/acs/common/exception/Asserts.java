package com.ccy1277.acs.common.exception;

import com.ccy1277.acs.common.api.IResultCode;

/**
 * 自定义断言类
 * created by ccy on 2022/5/9
 */
public class Asserts {
    public static void throwException(String msg){
        throw new ApiException(msg);
    }

    public static void throwException(IResultCode iResultCode){
        throw new ApiException(iResultCode);
    }
}
