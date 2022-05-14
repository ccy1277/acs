package com.ccy1277.acs.sys.service.cache.impl;

import com.ccy1277.acs.common.service.RedisService;
import com.ccy1277.acs.sys.model.Resource;
import com.ccy1277.acs.sys.model.User;
import com.ccy1277.acs.sys.service.cache.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户缓存管理实现类
 * created by ccy on 2022/5/9
 */
@Service
public class UserCacheServiceImpl implements UserCacheService {
    @Autowired
    private RedisService redisService;
    @Value("${redis.expire}")
    private long REDIS_KEY_EXPIRE;
    @Value("${redis.key.user}")
    private String REDIS_KEY_ADMIN;
    @Value("${redis.key.resources}")
    private String REDIS_KEY_RESOURCES;

    @Override
    public User getUser(String username) {
        return (User)redisService.get(REDIS_KEY_ADMIN + ":" + username);
    }

    @Override
    public void setUser(User user) {
        redisService.set(REDIS_KEY_ADMIN + ":" + user.getUsername(), user, REDIS_KEY_EXPIRE);
    }

    @Override
    public boolean deleteUser(String username) {
        return redisService.delete(REDIS_KEY_ADMIN + ":" + username);
    }

    @Override
    public boolean deleteResourceList(Long id) {
        return redisService.delete(REDIS_KEY_RESOURCES + ":" + id);
    }

    @Override
    public List<Resource> getResourceList(Long id) {
        return (List<Resource>) redisService.get(REDIS_KEY_RESOURCES + ":" + id);
    }

    @Override
    public void setResourceList(Long id, List<Resource> resources) {
        redisService.set(REDIS_KEY_RESOURCES + ":" + id, resources, REDIS_KEY_EXPIRE);
    }
}
