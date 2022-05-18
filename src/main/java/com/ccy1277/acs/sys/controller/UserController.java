package com.ccy1277.acs.sys.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.common.api.CommonPage;
import com.ccy1277.acs.common.api.CommonResult;
import com.ccy1277.acs.sys.dto.UserDto;
import com.ccy1277.acs.sys.model.Role;
import com.ccy1277.acs.sys.model.User;
import com.ccy1277.acs.sys.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理功能控制器
 * @author ccy1277
 * @since 2022-05-08
 */
@Api(tags = "UserController", description = "用户功能控制器")
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation("用户登录并返回token")
    @PostMapping("/login")
    public CommonResult login(@Validated @RequestBody UserDto userDto){
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
    public CommonResult<User> register(@Validated @RequestBody UserDto userDto){
        User user = userService.register(userDto);
        if(user == null){
            return CommonResult.failed();
        }
        return CommonResult.success(user, "注册成功");
    }

    @ApiOperation("用户退出登录")
    @GetMapping("/logout")
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
                                                          @Min(1) @RequestParam(defaultValue = "3") Integer pageSize,
                                                          @Min(1) @RequestParam(defaultValue = "1") Integer pageNum){
        Page<User> users = userService.getUserPagesByName(username, pageSize, pageNum);
        if(users != null){
            return CommonResult.success(users);
        }
        return CommonResult.failed("获取用户列表信息异常");
    }

    @ApiOperation("编辑用户基本信息")
    @PostMapping("/edit")
    public CommonResult editInfo(@Validated @RequestBody UserDto userDto){
        if(userService.updateUserById(userDto)){
            return CommonResult.success(userDto.getId() + "用户更新信息成功");
        }
        return CommonResult.failed(userDto.getId() + "用户更新信息失败");
    }

    @ApiOperation("删除用户")
    @PostMapping("/delete/{id}")
    public CommonResult deleteUser(@PathVariable Long id){
        if(userService.deleteUserById(id)){
            return CommonResult.success(id + "用户删除成功");
        }
        return CommonResult.failed(id + "用户删除失败");
    }

    @ApiOperation("获取指定用户的角色信息")
    @GetMapping("/urr/{id}")
    public CommonResult<List<Role>> givenUserRoles(@PathVariable Long id){
        List<Role> roles = userService.getRoleDetails(id);
        if(roles != null){
            return CommonResult.success(roles);
        }
        return CommonResult.failed("获取" + id + "用户的角色信息失败");
    }

    @ApiOperation("给用户分配角色")
    @PostMapping("/role/update/{userId}")
    public CommonResult updateUserRoleRelation(@PathVariable Long userId, List<Long> roleIds){
        if(userService.updateUserRole(userId, roleIds)){
            return CommonResult.success(userId + "用户分配角色成功");
        }
        return CommonResult.failed(userId + "用户分配角色失败");
    }
}

