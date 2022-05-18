package com.ccy1277.acs.sys.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.common.api.CommonPage;
import com.ccy1277.acs.common.api.CommonResult;
import com.ccy1277.acs.sys.model.Resource;
import com.ccy1277.acs.sys.service.ResourceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资源管理功能控制器
 * @author ccy1277
 * @since 2022-05-08
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @ApiOperation("分页查看菜单列表")
    @GetMapping("/list")
    public CommonResult<CommonPage<Resource>> listPagesByName(@RequestParam(required = false) String resourceName,
                                                              @RequestParam(defaultValue = "3") Integer pageSize,
                                                              @RequestParam(defaultValue = "1") Integer pageNum){
        Page<Resource> resources = resourceService.getResourcePagesByName(resourceName, pageSize, pageNum);
        if(resources != null){
            return CommonResult.success(resources);
        }
        return CommonResult.failed("获取资源列表信息异常");
    }
}

