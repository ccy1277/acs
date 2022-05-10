package com.ccy1277.acs.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccy1277.acs.common.exception.ApiException;
import com.ccy1277.acs.common.result.ResultCode;
import com.ccy1277.acs.common.utils.JwtTokenUtil;
import com.ccy1277.acs.sys.model.UserDetails;
import com.ccy1277.acs.sys.mapper.ResourceMapper;
import com.ccy1277.acs.sys.model.Resource;
import com.ccy1277.acs.sys.model.User;
import com.ccy1277.acs.sys.mapper.UserMapper;
import com.ccy1277.acs.sys.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccy1277.acs.sys.service.cache.UserCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务实现类
 * @author ccy1277
 * @since 2022-05-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserCacheService userCacheService;
    @Autowired(required = false)
    private ResourceMapper resourceMapper;

    @Override
    public User getUserByUserName(String username) {
        // 尝试直接从缓存中得到用户信息
        User user = userCacheService.getUser(username);
        if(user != null){
            return user;
        }
        // 缓存中没有 去数据库里查询
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        // 使用lambda方式可以在编译期发现拼写报错
        userWrapper.lambda().eq(User::getUsername, username);
        // MP 查询用户信息列表
        List<User> users = list(userWrapper);
        if(users != null && users.size() > 0){
            user = users.get(0);
            // 存入缓存
            userCacheService.setUser(user);
            return user;
        }
        throw new ApiException(ResultCode.MSG_NOTFOUND);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = getUserByUserName(username);
        if(user != null){
            List<Resource> resources = getResourceList(user.getId());
            return new UserDetails(user, resources);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public List<Resource> getResourceList(Long id) {
        List<Resource> resources = userCacheService.getResourceList(id);
        // 尝试从缓存中读取用户权限
        if(resources != null && resources.size() > 0){
            return resources;
        }
        // 缓存不存在
        resources = resourceMapper.getResourceList(id);
        if(resources != null && resources.size() > 0){
            userCacheService.setResourceList(id, resources);
            return resources;
        }

        return null;
    }
}
