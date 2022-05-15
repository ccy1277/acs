package com.ccy1277.acs.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.sys.dto.UserDto;
import com.ccy1277.acs.sys.model.Resource;
import com.ccy1277.acs.sys.model.Role;
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
     * 注册
     * @param userDto 注册信息
     * @return User
     */
    User register(UserDto userDto);

    /**
     * 登录
     * @return token
     */
    String login(String username, String password);

    /**
     * 退出登录
     */
    boolean logout(String username);

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
    List<Resource> getResourceList(Long id);

    /**
     * 分页获取用户列表
     * @param username 用户名 （可选）
     * @param pageSize 页的大小
     * @param pageNum 页数
     * @return Page<User>
     */
    Page<User> getUserPagesByName(String username, Integer pageSize, Integer pageNum);

    /**
     * 修改用户信息
     */
    boolean updateUserById(User user);

    /**
     * 删除用户信息
     */
    boolean deleteUserById(Long id);

    /**
     * 获取指定用户的详细角色信息
     */
    List<Role> getRoleDetails(Long id);

    /**
     * 更新用户的角色
     */
    boolean updateUserRole(Long userId, List<Long> roleIds);
}
