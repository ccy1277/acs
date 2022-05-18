package com.ccy1277.acs.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.sys.dto.ResourceDto;
import com.ccy1277.acs.sys.model.Resource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 资源服务接口类
 * @author ccy1277
 * @since 2022-05-08
 */
public interface ResourceService extends IService<Resource> {
    /**
     * 分页获取资源列表
     * @param resourceName 资源名（可选）
     * @param pageSize 页大小
     * @param pageNum 页数
     * @return Page<Resource>
     */
    Page<Resource> getResourcePagesByName(String resourceName, Integer pageSize, Integer pageNum);

    /**
     * 添加资源
     */
    boolean addResource(ResourceDto resourceDto);

    /**
     * 编辑资源信息
     */
    boolean updateResource(ResourceDto resourceDto);

    /**
     * 删除资源信息
     */
    boolean deleteResource(Long id);

    /**
     * 根据资源id查找对应的用户关系
     */
    List<Long> getUserRoleRelation(Long resourceId);
}
