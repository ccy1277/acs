package com.ccy1277.acs.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.sys.model.Resource;
import com.ccy1277.acs.sys.mapper.ResourceMapper;
import com.ccy1277.acs.sys.service.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 资源服务实现类
 * @author ccy1277
 * @since 2022-05-08
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Override
    public Page<Resource> getResourcePagesByName(String resourceName, Integer pageSize, Integer pageNum) {
        Page<Resource> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();

        if(resourceName != null && !resourceName.equals("")){
            wrapper.lambda().like(Resource::getName, resourceName);
        }
        return page(page, wrapper);
    }
}
