package com.ccy1277.acs.sys.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.common.api.CommonPage;
import com.ccy1277.acs.common.api.CommonResult;
import com.ccy1277.acs.sys.dto.MenuDto;
import com.ccy1277.acs.sys.model.Menu;
import com.ccy1277.acs.sys.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

/**
 * 菜单管理功能控制器
 * @author ccy1277
 * @since 2022-05-08
 */
@RestController
@RequestMapping("/menu")
@Api(tags = "MenuController", description = "菜单功能控制器")
@Validated
public class MenuController {
    @Autowired
    private MenuService menuService;

    @ApiOperation("分页查看菜单列表")
    @GetMapping("/list")
    public CommonResult<CommonPage<Menu>> listPagesByName(@RequestParam(required = false) String menuName,
                                                          @Min(1) @RequestParam(defaultValue = "3") Integer pageSize,
                                                          @Min(1) @RequestParam(defaultValue = "1") Integer pageNum){
        Page<Menu> menus = menuService.getMenuPagesByName(menuName, pageSize, pageNum);
        if(menus != null){
            return CommonResult.success(menus, "获取菜单列表信息成功");
        }
        return CommonResult.failed("获取菜单列表信息异常");
    }

    @ApiOperation("创建菜单")
    @PostMapping("/add")
    public CommonResult addMenu(@Validated(MenuDto.save.class) @RequestBody MenuDto menuDto){
        if(menuService.addMenu(menuDto)){
            return CommonResult.success(null, "菜单添加成功");
        }else{
            return CommonResult.failed("菜单添加失败");
        }
    }

    @ApiOperation("更新菜单信息")
    @PostMapping("/edit")
    public CommonResult editInfo(@Validated(MenuDto.update.class) @RequestBody MenuDto menuDto){
        if(menuService.updateMenu(menuDto)){
            return CommonResult.success(null, "菜单更新成功");
        }else{
            return CommonResult.failed("菜单更新失败");
        }
    }

    @ApiOperation("删除菜单信息")
    @GetMapping("/delete/{id}")
    public CommonResult deleteMenu(@PathVariable Long id){
        if(menuService.deleteMenu(id)){
            return CommonResult.success(null, "菜单删除成功");
        }else{
            return CommonResult.failed("菜单删除失败");
        }
    }
}

