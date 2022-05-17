package com.ccy1277.acs.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * 用户登录注册dto
 * created by ccy on 2022/5/10
 */
@Data
public class UserDto {
    @ApiModelProperty(value = "角色id")
    private String id;

    @NotEmpty(groups = {login.class, register.class})
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @NotEmpty(groups = {login.class, register.class})
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(value = "头像")
    private String profile;

    @Email(groups = {update.class, register.class})
    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "介绍")
    private String des;

    /**
     * 注册校验规则
     */
    public interface register{}

    /**
     * 登录校验规则
     */
    public interface login{}

    /**
     * 修改信息校验规则
     */
    public interface update{}
}
