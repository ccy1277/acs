package com.ccy1277.acs.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.sys.model.Menu;
import com.ccy1277.acs.sys.model.Resource;
import com.ccy1277.acs.sys.model.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccy1277.acs.sys.model.UserRoleRelation;

import java.util.List;

/**
 * 角色服务接口
 * @author ccy1277
 * @since 2022-05-08
 */
public interface RoleService extends IService<Role> {
    /**
     * 分页获取角色列表
     * @param roleName 角色名（可选）
     * @param pageSize 页大小
     * @param pageNum 页数
     * @return Page<Role>
     */
    Page<Role> getRolePagesByName(String roleName, Integer pageSize, Integer pageNum);

    /**
     * 添加角色
     */
    boolean addRole(Role role);

    /**
     * 编辑角色基本信息
     */
    boolean updateRole(Role role);

    /**
     * 删除指定角色
     */
    boolean deleteRole(Long id);

    /**
     * 更改角色的状态
     */
    boolean actRole(Long id, Integer status);

    /**
     * 获取角色的菜单
     */
    List<Menu> getRoleMenu(Long id);

    /**
     * 为角色分配菜单
     */
    boolean updateRoleMenu(Long id, List<Long> menuIds);

    /**
     * 获取角色的资源
     */
    List<Resource> getRoleResource(Long id);

    /**
     * 为角色分配资源
     */
    boolean updateRoleResource(Long id, List<Long> resourceIds);

    /**
     * 根据角色id查找对应的用户关系
     */
    List<UserRoleRelation> getUserRoleRelation(Long roleId);
}
