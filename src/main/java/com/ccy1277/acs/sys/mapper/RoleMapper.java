package com.ccy1277.acs.sys.mapper;

import com.ccy1277.acs.sys.model.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 角色mapper
 * @author ccy1277
 * @since 2022-05-08
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 查询某用户的角色信息
     */
    List<Role> getRolesByUserId(Long userId);
}
