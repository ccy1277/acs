package com.ccy1277.acs.sys.controller;


import com.ccy1277.acs.common.api.CommonResult;
import com.ccy1277.acs.sys.dto.ResourceCatDto;
import com.ccy1277.acs.sys.dto.ResourceDto;
import com.ccy1277.acs.sys.model.ResourceCategory;
import com.ccy1277.acs.sys.service.ResourceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 资源分类控制器
 * @author ccy1277
 * @since 2022-05-08
 */
@RestController
@RequestMapping("/resourceCategory")
@Api(tags = "ResourceCategoryController", description = "资源分类控制器")
public class ResourceCategoryController {
    @Autowired
    private ResourceCategoryService resourceCategoryService;

    @ApiOperation("获取所有分类信息")
    @GetMapping("/list")
    public CommonResult<List<ResourceCategory>> getResourcesList(){
        List<ResourceCategory> resourceCategories = resourceCategoryService.list();
        if(resourceCategories != null){
            return CommonResult.success(resourceCategories, "获取分类成功");
        }else{
            return CommonResult.failed("获取分类失败");
        }
    }

    @ApiOperation("添加分类")
    @PostMapping("/add")
    public CommonResult addResourceCat(@Validated(value = {ResourceCatDto.save.class}) @RequestBody ResourceCatDto resourceCatDto){
        if(resourceCategoryService.addResourceCat(resourceCatDto)){
            return CommonResult.success(null, "添加分类成功");
        }else{
            return CommonResult.failed("添加分类失败");
        }
    }


    @ApiOperation("删除分类")
    @GetMapping("/delete/{id}")
    public CommonResult deleteResourceCat(@PathVariable Long id){
        if(resourceCategoryService.removeById(id)){
            return CommonResult.success(null, "删除分类成功");
        }else{
            return CommonResult.failed("删除分类失败");
        }
    }

    @ApiOperation("更新分类")
    @PostMapping("/update")
    public CommonResult updateResourceCat(@Validated(value = {ResourceCatDto.update.class}) @RequestBody ResourceCatDto resourceCatDto){
        if(resourceCategoryService.updateResourceCat(resourceCatDto)){
            return CommonResult.success(null, "更新分类成功");
        }else{
            return CommonResult.failed("更新分类失败");
        }
    }


}

