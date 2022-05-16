package com.ccy1277.acs.sys.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.common.result.CommonPage;
import com.ccy1277.acs.common.result.CommonResult;
import com.ccy1277.acs.sys.model.Menu;
import com.ccy1277.acs.sys.model.Resource;
import com.ccy1277.acs.sys.model.Role;
import com.ccy1277.acs.sys.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation("查看指定角色的信息")
    @GetMapping("/info/{id}")
    public CommonResult<Role> givenInfo(@PathVariable Long id){
        Role role = roleService.getById(id);
        if(role != null){
            return CommonResult.success(role, "查询成功");
        }else{
            return CommonResult.failed("查询失败");
        }
    }

    @ApiOperation("增加角色")
    @PostMapping("/add")
    public CommonResult addRole(Role role){
        if(roleService.addRole(role)){
            return CommonResult.success(null, role.getName() + "添加成功");
        }else{
            return CommonResult.failed(role.getName() + "添加失败");
        }
    }

    @ApiOperation("更新角色信息")
    @PostMapping("/edit")
    public CommonResult editInfo(Role role){
        if(roleService.updateRole(role)){
            return CommonResult.success(null, role.getName() + "更新成功");
        }else{
            return CommonResult.failed(role.getName() + "更新失败");
        }
    }

    @ApiOperation("删除指定角色")
    @GetMapping("/delete/{id}")
    public CommonResult deleteRole(@PathVariable Long id){
        if(roleService.deleteRole(id)){
            return CommonResult.success(null, "删除成功");
        }else{
            return CommonResult.failed("删除失败");
        }
    }

    @ApiOperation("获取指定的角色菜单")
    @GetMapping("/rmr/{id}")
    public CommonResult<List<Menu>> givenRoleMenu(@PathVariable Long id){
        List<Menu> menus = roleService.getRoleMenu(id);
        if(menus != null){
            return CommonResult.success(menus, "获取角色菜单成功");
        }else{
            return CommonResult.failed("获取角色菜单失败");
        }
    }

    @ApiOperation("获取指定的角色资源")
    @GetMapping("/rrr/{id}")
    public CommonResult<List<Resource>> givenRoleResource(@PathVariable Long id){
        List<Resource> resources = roleService.getRoleResource(id);
        if(resources != null){
            return CommonResult.success(resources, "获取角色资源成功");
        }else{
            return CommonResult.failed("获取角色资源失败");
        }
    }

    @ApiOperation("为角色分配菜单")
    @PostMapping("/menu/update")
    public CommonResult updateRoleMenuRelation(Long id, List<Long> menuIds){
        if(roleService.updateRoleMenu(id, menuIds)){
            return CommonResult.success(null, "分配成功");
        }else{
            return CommonResult.failed("分配失败");
        }
    }


    @ApiOperation("为角色分配资源")
    @PostMapping("/resource/update")
    public CommonResult updateRoleResourceRelation(Long id, List<Long> resourceIds){
        if(roleService.updateRoleResource(id, resourceIds)){
            return CommonResult.success(null, "分配成功");
        }else{
            return CommonResult.failed("分配失败");
        }
    }
}

