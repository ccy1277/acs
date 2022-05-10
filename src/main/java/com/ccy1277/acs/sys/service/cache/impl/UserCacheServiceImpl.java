package com.ccy1277.acs.sys.service.cache.impl;

import com.ccy1277.acs.sys.model.Resource;
import com.ccy1277.acs.sys.model.User;
import com.ccy1277.acs.sys.service.cache.UserCacheService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户缓存管理实现类
 * created by ccy on 2022/5/9
 */
@Service
public class UserCacheServiceImpl implements UserCacheService {
    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public List<Resource> getResourceList(Long id) {
        return null;
    }

    @Override
    public void setResourceList(Long adminId, List<Resource> resources) {

    }
}
