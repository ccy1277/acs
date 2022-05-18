package com.ccy1277.acs.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.common.exception.Asserts;
import com.ccy1277.acs.sys.dto.RoleDto;
import com.ccy1277.acs.sys.mapper.MenuMapper;
import com.ccy1277.acs.sys.mapper.ResourceMapper;
import com.ccy1277.acs.sys.model.*;
import com.ccy1277.acs.sys.mapper.RoleMapper;
import com.ccy1277.acs.sys.service.RoleMenuRelationService;
import com.ccy1277.acs.sys.service.RoleResourceRelationService;
import com.ccy1277.acs.sys.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccy1277.acs.sys.service.UserRoleRelationService;
import com.ccy1277.acs.sys.service.cache.UserCacheService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 角色服务实现类
 * @author ccy1277
 * @since 2022-05-08
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private UserCacheService userCacheService;
    @Autowired
    private UserRoleRelationService userRoleRelationService;
    @Autowired(required = false)
    private MenuMapper menuMapper;
    @Autowired(required = false)
    private ResourceMapper resourceMapper;
    @Autowired
    private RoleMenuRelationService roleMenuRelationService;
    @Autowired
    private RoleResourceRelationService roleResourceRelationService;

    @Override
    public Page<Role> getRolePagesByName(String roleName, Integer pageSize, Integer pageNum) {
        Page<Role> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();

        if(StrUtil.isNotEmpty(roleName)){
            wrapper.lambda().like(Role::getName, roleName);
        }
        return page(page, wrapper);
    }

    @Override
    public boolean addRole(RoleDto roleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        // 检查角色是否已经存在
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Role::getName, role.getName());
        List<Role> roles = this.list(wrapper);

        if(roles.size() > 0){
            Asserts.throwException("角色已存在");
        }
        role.setCreateTime(new Date());
        role.setStatus(1);

        return this.save(role);
    }

    @Override
    public boolean updateRole(RoleDto roleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        return this.updateById(role);
    }

    @Override
    public boolean deleteRole(Long id) {
        // 删除了角色需要清除缓存
        userCacheService.deleteResourceListByRole(getUserRoleRelation(id));
        // 删除了角色还需要删除角色与资源的映射关系
        return this.removeById(id);
    }

    @Override
    public boolean actRole(Long id, Integer status) {
        return false;
    }

    @Override
    public List<Menu> getRoleMenu(Long id) {
        return menuMapper.getMenuByRoleId(id);
    }

    @Override
    public boolean updateRoleMenu(Long id, List<Long> menuIds) {
        // 删除旧的菜单
        QueryWrapper<RoleMenuRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RoleMenuRelation::getRoleId, id);
        roleMenuRelationService.remove(wrapper);
        // 分配新菜单
        List<RoleMenuRelation> roleMenuRelations = new ArrayList<>();
        for(Long menuId : menuIds){
            RoleMenuRelation roleMenuRelation = new RoleMenuRelation();
            roleMenuRelation.setMenuId(menuId);
            roleMenuRelation.setRoleId(id);
            roleMenuRelations.add(roleMenuRelation);
        }
        return roleMenuRelationService.saveBatch(roleMenuRelations);
    }

    @Override
    public List<Resource> getRoleResource(Long id) {
        return resourceMapper.getResourceListByRoleId(id);
    }

    @Override
    public boolean updateRoleResource(Long id, List<Long> resourceIds) {
        // 清除旧的资源
        QueryWrapper<RoleResourceRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RoleResourceRelation::getRoleId, id);
        roleResourceRelationService.remove(wrapper);
        // 分配新资源
        List<RoleResourceRelation> roleResourceRelations = new ArrayList<>();
        for(Long resourceId : resourceIds){
            RoleResourceRelation roleResourceRelation = new RoleResourceRelation();
            roleResourceRelation.setResourceId(resourceId);
            roleResourceRelation.setRoleId(id);
            roleResourceRelations.add(roleResourceRelation);
        }
        // 清除缓存
        userCacheService.deleteResourceListByRole(getUserRoleRelation(id));
        return roleResourceRelationService.saveBatch(roleResourceRelations);
    }

    @Override
    public List<UserRoleRelation> getUserRoleRelation(Long roleId) {
        QueryWrapper<UserRoleRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserRoleRelation::getRoleId, roleId);
        return userRoleRelationService.list(wrapper);
    }
}
