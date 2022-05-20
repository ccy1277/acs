package com.ccy1277.acs.sys.service;

import com.ccy1277.acs.sys.dto.ResourceCatDto;
import com.ccy1277.acs.sys.model.ResourceCategory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 资源分类服务接口
 * @author ccy1277
 * @since 2022-05-08
 */
public interface ResourceCategoryService extends IService<ResourceCategory> {
    /**
     * 增加新分类
     */
    boolean addResourceCat(ResourceCatDto resourceCatDto);

    /**
     * 更新分类
     */
    boolean updateResourceCat(ResourceCatDto resourceCatDto);
}
