package com.ccy1277.acs.sys.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.common.result.CommonPage;
import com.ccy1277.acs.common.result.CommonResult;
import com.ccy1277.acs.sys.model.Menu;
import com.ccy1277.acs.sys.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单管理功能控制器
 * @author ccy1277
 * @since 2022-05-08
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @ApiOperation("根据菜单名分页查看菜单列表")
    @GetMapping("/list")
    public CommonResult<CommonPage<Menu>> listPagesByName(@RequestParam(required = false) String menuName,
                                                          @RequestParam(defaultValue = "3") Integer pageSize,
                                                          @RequestParam(defaultValue = "1") Integer pageNum){
        Page<Menu> menus = menuService.getMenuPagesByName(menuName, pageSize, pageNum);
        if(menus != null){
            return CommonResult.success(menus);
        }
        return CommonResult.failed("获取菜单列表信息异常");
    }
}

