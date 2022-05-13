package com.ccy1277.acs.config;

import com.ccy1277.acs.security.config.SpringSecurityConfig;
import com.ccy1277.acs.security.service.DynamicDataSourceService;
import com.ccy1277.acs.sys.model.Resource;
import com.ccy1277.acs.sys.service.ResourceService;
import com.ccy1277.acs.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SpringSecurity 配置扩展
 * created by ccy on 2022/5/9
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class AcsSecurityConfig extends SpringSecurityConfig {
    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceService;

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> userService.loadUserByUsername(username);
    }

    @Bean
    public DynamicDataSourceService dynamicDataSourceService(){
        return new DynamicDataSourceService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<Resource> resources = resourceService.list();

                for(Resource resource : resources){
                    map.put(resource.getUrl(), new SecurityConfig(resource.getId() + ":" + resource.getName()));
                }
                return map;
            }
        };
    }
}
