package com.ccy1277.acs.sys.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.common.result.CommonPage;
import com.ccy1277.acs.common.result.CommonResult;
import com.ccy1277.acs.sys.model.Role;
import com.ccy1277.acs.sys.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色管理功能控制器
 * @author ccy1277
 * @since 2022-05-08
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ApiOperation("根据角色名分页查看角色列表")
    @GetMapping("/list")
    public CommonResult<CommonPage<Role>> listPagesByName(@RequestParam(required = false) String roleName,
                                                          @RequestParam(defaultValue = "3") Integer pageSize,
                                                          @RequestParam(defaultValue = "1") Integer pageNum){
        Page<Role> roles = roleService.getRolePagesByName(roleName, pageSize, pageNum);
        if(roles != null){
            return CommonResult.success(roles);
        }
        return CommonResult.failed("获取角色列表信息异常");
    }
}

