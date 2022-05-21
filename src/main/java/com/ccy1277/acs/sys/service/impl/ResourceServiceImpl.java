package com.ccy1277.acs.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.common.exception.Asserts;
import com.ccy1277.acs.sys.dto.ResourceDto;
import com.ccy1277.acs.sys.model.Resource;
import com.ccy1277.acs.sys.mapper.ResourceMapper;
import com.ccy1277.acs.sys.model.RoleResourceRelation;
import com.ccy1277.acs.sys.service.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccy1277.acs.sys.service.RoleResourceRelationService;
import com.ccy1277.acs.sys.service.cache.UserCacheService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 资源服务实现类
 * @author ccy1277
 * @since 2022-05-08
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {
    @Autowired(required = false)
    private ResourceMapper resourceMapper;
    @Autowired
    private UserCacheService userCacheService;
    @Autowired
    private RoleResourceRelationService roleResourceRelationService;

    @Override
    public Page<Resource> getResourcePagesByName(String resourceName, Integer pageSize, Integer pageNum) {
        Page<Resource> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();

        if(StrUtil.isNotEmpty(resourceName)){
            wrapper.lambda().like(Resource::getName, resourceName);
        }
        return page(page, wrapper);
    }

    @Override
    public boolean addResource(ResourceDto resourceDto) {
        Resource resource = new Resource();
        BeanUtils.copyProperties(resourceDto, resource);
        // 查询资源是否已经存在
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Resource::getName, resource.getName());
        List<Resource> resources = this.list(wrapper);

        if(resources.size() > 0){
            Asserts.throwException("资源已存在");
        }
        resource.setCreateTime(new Date());

        return this.save(resource);
    }

    @Override
    public boolean updateResource(ResourceDto resourceDto) {
        Resource resource = new Resource();
        BeanUtils.copyProperties(resourceDto, resource);
        // 更新了资源信息需要清除缓存
        userCacheService.deleteResourceListBatch(getUserRoleRelation(resource.getId()));
        return this.updateById(resource);
    }

    @Override
    public boolean deleteResource(Long id) {
        // 删除资源需要清除缓存
        userCacheService.deleteResourceListBatch(getUserRoleRelation(id));
        // 删除资源需要清除资源与角色的关系
        QueryWrapper<RoleResourceRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RoleResourceRelation::getResourceId, id);
        roleResourceRelationService.remove(wrapper);
        return this.removeById(id);
    }

    @Override
    public List<Long> getUserRoleRelation(Long resourceId) {
        return resourceMapper.getUserIdListById(resourceId);
    }
}
