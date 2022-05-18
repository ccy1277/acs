package com.ccy1277.acs.common.api;

/**
 * enum 定义常用的 Code
 * created by ccy on 2022/5/9
 */
public enum ResultCode implements IResultCode{
    SUCCESS(200, "success"),
    FAILED(500, "failed"),
    VALIDATE_FAILED(404, "validated failed"),
    LOGIN_FAILED(401, "用户名或密码错误"),
    UNAUTHORIZED(401, "not logged in or the token has expired"),
    FORBIDDEN(403, "no relevant permissions"),
    MSG_NOTFOUND(404, "查询的信息不存在");

    private Integer code;
    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
