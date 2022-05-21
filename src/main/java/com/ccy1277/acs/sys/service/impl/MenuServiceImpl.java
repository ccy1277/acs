package com.ccy1277.acs.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.common.exception.Asserts;
import com.ccy1277.acs.sys.dto.MenuDto;
import com.ccy1277.acs.sys.model.Menu;
import com.ccy1277.acs.sys.mapper.MenuMapper;
import com.ccy1277.acs.sys.model.RoleMenuRelation;
import com.ccy1277.acs.sys.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccy1277.acs.sys.service.RoleMenuRelationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 菜单服务实现类接口
 * @author ccy1277
 * @since 2022-05-08
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private RoleMenuRelationService roleMenuRelationService;

    @Override
    public Page<Menu> getMenuPagesByName(String menuName, Integer pageSize, Integer pageNum) {
        Page<Menu> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();

        if(StrUtil.isNotEmpty(menuName)){
            wrapper.lambda().like(Menu::getName, menuName);
        }
        return page(page, wrapper);
    }

    @Override
    public boolean addMenu(MenuDto menuDto) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDto, menu);
        // 查询菜单是否已经存在
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Menu::getName, menu.getName());
        List<Menu> menus = this.list(wrapper);

        if(menus.size() > 0){
            Asserts.throwException("菜单已存在");
        }
        menu.setCreateTime(new Date());
        menu.setSort(0);

        if(menu.getParentId() == null || menu.getParentId() == 0){
            menu.setLevel(0);
        }else{
            menu.setLevel(this.updateLevel(menu.getParentId()));
        }

        return this.save(menu);
    }

    @Override
    public boolean updateMenu(MenuDto menuDto) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDto, menu);
        if(menu.getParentId() == null || menu.getParentId() == 0){
            menu.setLevel(0);
        }else{
            menu.setLevel(this.updateLevel(menu.getParentId()));
        }

        return this.updateById(menu);
    }

    @Override
    public boolean deleteMenu(Long id) {
        // 删除资源需要清除菜单与角色的关系
        QueryWrapper<RoleMenuRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RoleMenuRelation::getMenuId, id);
        roleMenuRelationService.remove(wrapper);
        return this.removeById(id);
    }

    /**
     * 修改层级
     * @param parentId 父菜单Id
     * @return 返回子菜单的层级
     */
    private Integer updateLevel(Long parentId){
        return this.getById(parentId).getLevel() + 1;
    }
}
