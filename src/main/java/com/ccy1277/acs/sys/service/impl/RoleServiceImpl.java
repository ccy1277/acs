package com.ccy1277.acs.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.sys.model.Role;
import com.ccy1277.acs.sys.mapper.RoleMapper;
import com.ccy1277.acs.sys.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色服务实现类
 * @author ccy1277
 * @since 2022-05-08
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public Page<Role> getRolePagesByName(String roleName, Integer pageSize, Integer pageNum) {
        Page<Role> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();

        if(roleName != null && !roleName.equals("")){
            wrapper.lambda().like(Role::getName, roleName);
        }
        return page(page, wrapper);
    }
}
