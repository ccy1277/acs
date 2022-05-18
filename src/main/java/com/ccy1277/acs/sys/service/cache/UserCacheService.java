package com.ccy1277.acs.sys.service.cache;

import com.ccy1277.acs.sys.model.Resource;
import com.ccy1277.acs.sys.model.User;
import com.ccy1277.acs.sys.model.UserRoleRelation;

import java.util.List;

/**
 * 用户缓存管理服务
 * created by ccy on 2022/5/9
 */
public interface UserCacheService {
    /**
     * 通过用户名获取用户缓存信息
     */
    User getUser(String username);

    /**
     * 缓存用户信息
     */
    void setUser(User user);

    /**
     * 删除用户缓存信息
     */
    boolean deleteUser(String username);

    /**
     * 删除用户权限缓存
     */
    boolean deleteResourceList(Long id);

    /**
     * 根据用户id批量删除相应的权限缓存
     */
    boolean deleteResourceListBatch(List<Long> ids);

    /**
     * 通过用户id获取权限缓存信息
     */
    List<Resource> getResourceList(Long id);

    /**
     * 缓存用户权限信息
     */
    void setResourceList(Long id, List<Resource> resources);
}
