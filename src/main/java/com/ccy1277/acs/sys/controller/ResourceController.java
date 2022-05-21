package com.ccy1277.acs.sys.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.common.api.CommonPage;
import com.ccy1277.acs.common.api.CommonResult;
import com.ccy1277.acs.sys.dto.ResourceDto;
import com.ccy1277.acs.sys.model.Resource;
import com.ccy1277.acs.sys.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

/**
 * 资源管理功能控制器
 * @author ccy1277
 * @since 2022-05-08
 */
@RestController
@RequestMapping("/resource")
@Api(tags = "ResourceController", description = "资源功能控制器")
@Validated
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @ApiOperation("分页查看资源列表")
    @GetMapping("/list")
    public CommonResult<CommonPage<Resource>> listPagesByName(@RequestParam(required = false) String resourceName,
                                                              @Min(1) @RequestParam(defaultValue = "3") Integer pageSize,
                                                              @Min(1) @RequestParam(defaultValue = "1") Integer pageNum){
        Page<Resource> resources = resourceService.getResourcePagesByName(resourceName, pageSize, pageNum);
        if(resources != null){
            return CommonResult.success(resources);
        }
        return CommonResult.failed("获取资源列表信息异常");
    }

    @ApiOperation("创建资源")
    @PostMapping("/add")
    public CommonResult addResource(@Validated(ResourceDto.save.class) @RequestBody ResourceDto resourceDto){
        if(resourceService.addResource(resourceDto)){
            return CommonResult.success(null, "资源创建成功");
        }else{
            return CommonResult.failed("资源创建失败");
        }
    }

    @ApiOperation("更新资源")
    @PostMapping("/edit")
    public CommonResult editInfo(@Validated(ResourceDto.update.class) @RequestBody ResourceDto resourceDto){
        if(resourceService.updateResource(resourceDto)){
            return CommonResult.success(null, "资源更新成功");
        }else{
            return CommonResult.failed("资源更新失败");
        }
    }

    @ApiOperation("删除指定资源")
    @PostMapping("/delete/{id}")
    public CommonResult deleteResource(@PathVariable Long id){
        if(resourceService.deleteResource(id)){
            return CommonResult.success(null, "资源删除成功");
        }else{
            return CommonResult.failed("资源删除失败");
        }
    }
}

