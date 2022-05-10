package com.ccy1277.acs.sys.service;

import com.ccy1277.acs.sys.model.Resource;
import com.ccy1277.acs.sys.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * 用户服务接口
 * @author ccy1277
 * @since 2022-05-08
 */
public interface UserService extends IService<User> {
    /**
     * 根据用户名获得后台管理员
     */
    User getUserByUserName(String username);

    /**
     * 根据用户名获取用户账号信息+权限信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 获取用户的可访问资源
     */
    List<Resource> getResourceList(Long adminId);
}
