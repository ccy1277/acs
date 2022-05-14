package com.ccy1277.acs.sys.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.common.result.CommonPage;
import com.ccy1277.acs.common.result.CommonResult;
import com.ccy1277.acs.sys.dto.UserDto;
import com.ccy1277.acs.sys.model.User;
import com.ccy1277.acs.sys.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        return CommonResult.success(user, "注册成功");
    }

    @ApiOperation("用户退出登录")
    @PostMapping("/logout")
    public CommonResult logout(Principal principal){
        if(userService.logout(principal.getName())){
            return CommonResult.success(null, "退出登录");
        }
        return CommonResult.failed("用户尚未登录");
    }

    @ApiOperation("查看当前登录用户信息")
    @GetMapping("/info")
    public CommonResult<User> loginInfo(Principal principal){
        User user = userService.getUserByUserName(principal.getName());
        // 角色信息 菜单信息
        if(user != null){
            return CommonResult.success(user);
        }
        return CommonResult.failed("获取当前用户信息异常");
    }

    @ApiOperation("查看指定用户信息")
    @GetMapping("/{id}")
    public CommonResult<User> givenInfo(@PathVariable Long id){
        User user = userService.getById(id);
        if(user != null){
            return CommonResult.success(user);
        }
        return CommonResult.failed("获取指定用户信息异常");
    }

    @ApiOperation("根据用户名分页查看用户列表")
    @GetMapping("/list")
    public CommonResult<CommonPage<User>> listPagesByName(@RequestParam(required = false) String username,
                                                     @RequestParam(defaultValue = "3") Integer pageSize,
                                                     @RequestParam(defaultValue = "1") Integer pageNum){
        Page<User> users = userService.getUserPagesByName(username, pageSize, pageNum);
        if(users != null){
            return CommonResult.success(users);
        }
        return CommonResult.failed("获取用户列表信息异常");
    }
}

