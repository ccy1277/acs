package com.ccy1277.acs.security.service;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * 提供url:资源map
 * created by ccy on 2022/5/12
 */
public interface DynamicDataSourceService {
    Map<String, ConfigAttribute> loadDataSource();
}
