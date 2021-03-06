package com.ccy1277.acs.sys.service.cache.impl;

import com.ccy1277.acs.common.service.RedisService;
import com.ccy1277.acs.sys.model.Resource;
import com.ccy1277.acs.sys.model.User;
import com.ccy1277.acs.sys.model.UserRoleRelation;
import com.ccy1277.acs.sys.service.cache.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public boolean deleteResourceListBatch(List<Long> ids) {
        if(ids == null){
            return false;
        }
        List<String> list = ids.stream()
                .map(id -> REDIS_KEY_RESOURCES + ":" + id)
                .collect(Collectors.toList());
        return redisService.delete(list) > 0;
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
