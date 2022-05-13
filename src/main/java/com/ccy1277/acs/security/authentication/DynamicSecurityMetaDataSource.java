package com.ccy1277.acs.security.authentication;

import cn.hutool.core.util.URLUtil;
import com.ccy1277.acs.security.service.DynamicDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 动态权限数据源: 获取动态权限规则
 * created by ccy on 2022/5/12
 */
public class DynamicSecurityMetaDataSource implements FilterInvocationSecurityMetadataSource {
    private static Map<String, ConfigAttribute> configAttributeMap = null;

    @Autowired
    private DynamicDataSourceService dynamicDataSourceService;

    // Spring容器初始化的时候执行
    @PostConstruct
    public void loadDataSource(){
        configAttributeMap = dynamicDataSourceService.loadDataSource();
    }

    public void clearDataSource(){
        configAttributeMap.clear();
        configAttributeMap = null;
    }

    /*
     * 获取某个安全对象object的所需要的权限信息(用于获取当前访问路径所需资源)
     * @param o 受保护的安全对象object
     * @return ConfigAttribute对象的集合,获取某个受保护的安全对象object的所需要的权限信息
     * @throws IllegalArgumentException 如果该安全对象object不被当前SecurityMetadataSource对象支持,则抛出异常IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if(configAttributeMap == null){
            this.loadDataSource();
        }
        List<ConfigAttribute> configAttributes = new ArrayList<>();
        // 获取当前访问路径
        String currentUrl = ((FilterInvocation)o).getRequestUrl();
        // 获取请求资源路径
        String needPath = URLUtil.getPath(currentUrl);

        PathMatcher pathMatcher = new AntPathMatcher();
        Iterator<String> iterator = configAttributeMap.keySet().iterator();
        while(iterator.hasNext()){
            String path = iterator.next();
            if(pathMatcher.match(path, needPath)){
                configAttributes.add(configAttributeMap.get(path));
            }
        }

        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /*
     * 告知调用者当前SecurityMetadataSource是否支持此类安全对象
     * @param aClass 安全对象的类型
     * @return true: 能对这类安全对象调用getAttributes方法
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
