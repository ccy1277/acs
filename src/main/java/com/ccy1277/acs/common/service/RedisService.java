package com.ccy1277.acs.common.service;

import java.util.List;

/**
 * Redis服务类
 * created by ccy on 2022/5/8
 */
public interface RedisService {
    /**
     * 存储数据
     */
    void set(String key, Object value);

    /**
     * 存储数据，同时设置过期时间
     * @param key 键
     * @param value 值
     * @param time 过期时间
     */
    void set(String key, Object value, long time);

    /**
     * 获取数据
     */
    Object get(String key);

    /**
     * 删除数据
     */
    boolean delete(String key);

    /**
     * 批量删除数据
     * @return 删除属性数量
     */
    long delete(List<String> list);

    /**
     * 设置过期时间
     */
    boolean expire(String key, long time);

    /**
     * 获取过期时间
     */
    long getExpire(String key);

    /**
     * 是否存在此属性
     */
    boolean isExistKey(String key);
}
