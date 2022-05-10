package com.ccy1277.acs.sys.mapper;

import com.ccy1277.acs.sys.model.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资源mapper
 * @author ccy1277
 * @since 2022-05-08
 */
public interface ResourceMapper extends BaseMapper<Resource> {
    /**
     * 获取用户所有可访问资源
     */
    List<Resource> getResourceList(@Param("userId") Long userId);
}
