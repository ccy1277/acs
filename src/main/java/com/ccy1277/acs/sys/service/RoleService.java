package com.ccy1277.acs.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.sys.model.Role;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
