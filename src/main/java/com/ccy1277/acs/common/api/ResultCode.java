package com.ccy1277.acs.common.api;

/**
 * enum 定义业务状态Code
 * created by ccy on 2022/5/9
 */
public enum ResultCode implements IResultCode{
    // 定义业务状态码（通用）
    SUCCESS(2000, "success"),
    FAILED(2001, "failed"),
    VALIDATE_FAILED(2002, "validated failed"),
    ERROR(2003, "unknown mistake"),

    LOGIN_FAILED(1000, "用户名或密码错误"),
    UNAUTHORIZED(1001, "用户尚未登录或登录信息过期"),
    FORBIDDEN(1002, "权限不足"),
    USER_NOTFOUND(1003, "用户不存在"),
    MSG_NOTFOUND(1003, "查询的信息不存在");

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
