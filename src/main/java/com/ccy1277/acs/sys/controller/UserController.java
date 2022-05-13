package com.ccy1277.acs.sys.controller;


import com.ccy1277.acs.common.result.CommonResult;
import com.ccy1277.acs.sys.dto.UserDto;
import com.ccy1277.acs.sys.model.User;
import com.ccy1277.acs.sys.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理功能控制器
 * @author ccy1277
 * @since 2022-05-08
 */
@Api(tags = "UserController", description = "用户功能控制器")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation("用户登录并返回token")
    @PostMapping("/login")
    public CommonResult login(UserDto userDto){
        String token = userService.login(userDto.getUsername(), userDto.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public CommonResult<User> register(UserDto userDto){
        User user = userService.register(userDto);
        if(user == null){
            return CommonResult.failed();
        }
        return CommonResult.success(user);
    }
}

