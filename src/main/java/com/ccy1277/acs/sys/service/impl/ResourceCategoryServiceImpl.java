package com.ccy1277.acs.sys.service.impl;

import com.ccy1277.acs.sys.dto.ResourceCatDto;
import com.ccy1277.acs.sys.model.ResourceCategory;
import com.ccy1277.acs.sys.mapper.ResourceCategoryMapper;
import com.ccy1277.acs.sys.service.ResourceCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *  资源分类服务实现类
 * @author ccy1277
 * @since 2022-05-08
 */
@Service
public class ResourceCategoryServiceImpl extends ServiceImpl<ResourceCategoryMapper, ResourceCategory> implements ResourceCategoryService {

    @Override
    public boolean addResourceCat(ResourceCatDto resourceCatDto) {
        ResourceCategory resourceCategory = new ResourceCategory();
        BeanUtils.copyProperties(resourceCatDto, resourceCategory);
        resourceCategory.setCreateTime(new Date());

        return this.save(resourceCategory);
    }

    @Override
    public boolean updateResourceCat(ResourceCatDto resourceCatDto) {
        ResourceCategory resourceCategory = new ResourceCategory();
        BeanUtils.copyProperties(resourceCatDto, resourceCategory);

        return this.updateById(resourceCategory);
    }
}
