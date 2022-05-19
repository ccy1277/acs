package com.ccy1277.acs.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccy1277.acs.sys.dto.MenuDto;
import com.ccy1277.acs.sys.model.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccy1277.acs.sys.model.Role;

/**
 * 菜单服务接口
 * @author ccy1277
 * @since 2022-05-08
 */
public interface MenuService extends IService<Menu> {
    /**
     * 分页获取菜单列表
     * @param menuName 菜单名（可选）
     * @param pageSize 页大小
     * @param pageNum 页数
     * @return Page<Menu>
     */
    Page<Menu> getMenuPagesByName(String menuName, Integer pageSize, Integer pageNum);

    /**
     * 增加后台菜单
     */
    boolean addMenu(MenuDto menuDto);

    /**
     * 更新后台菜单
     */
    boolean updateMenu(MenuDto menuDto);
}
