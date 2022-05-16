package com.ccy1277.acs.sys.mapper;

import com.ccy1277.acs.sys.model.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 菜单Mapper接口
 * @author ccy1277
 * @since 2022-05-08
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据角色id获取菜单
     */
    List<Menu> getMenuByRoleId(Long roleId);
}
