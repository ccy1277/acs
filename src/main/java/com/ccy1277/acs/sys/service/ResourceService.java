package com.ccy1277.acs.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.sys.model.Resource;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
