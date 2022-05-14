package com.ccy1277.acs.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.sys.model.Menu;
import com.ccy1277.acs.sys.mapper.MenuMapper;
import com.ccy1277.acs.sys.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 菜单服务实现类接口
 * @author ccy1277
 * @since 2022-05-08
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public Page<Menu> getMenuPagesByName(String menuName, Integer pageSize, Integer pageNum) {
        Page<Menu> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();

        if(menuName != null && menuName.equals("")){
            wrapper.lambda().like(Menu::getName, menuName);
        }
        return page(page, wrapper);
    }
}
