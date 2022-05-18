package com.ccy1277.acs.common.api;

/**
 * 自定义统一返回结果
 * created by ccy on 2022/5/9
 */
public class CommonResult<T> {
    // 状态码
    private Integer code;
    private String msg;
    private T data;

    public CommonResult() {
    }

    public CommonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CommonResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public long getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 成功返回结果
     */
    public static <T> CommonResult success(T data){
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     * @param msg 自定义成功提示
     */
    public static <T> CommonResult success(T data, String msg){
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult failed(){
        return new CommonResult<T>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage());
    }

    /**
     * 失败返回结果
     * @param msg 自定义失败提示信息
     */
    public static <T> CommonResult failed(String msg){
        return new CommonResult<T>(ResultCode.FAILED.getCode(), msg);
    }

    /**
     * 失败返回结果
     * @param errorCode 自定义返回码和失败提示信息
     */
    public static <T> CommonResult failed(IResultCode errorCode){
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 没有登录返回结果
     */
    public static <T> CommonResult unAuthorized(T data){
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailed() {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null);
    }

    /**
     * 参数验证失败返回结果
     * @param msg 自定义提示信息
     */
    public static <T> CommonResult<T> validateFailed(String msg) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), msg, null);
    }

    /**
     * 没有权限访问返回结果
     */
    public static <T> CommonResult forbidden(T data){
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }
}
