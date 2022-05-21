package com.ccy1277.acs.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 解决跨域
 * created by ccy on 2022/5/21
 */
@Configuration
public class CorsFilterConfig {
    @Bean
    public CorsFilter corsFilter() {
        //创建CorsConfiguration对象后添加配置
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //设置放行哪些原始域
        corsConfiguration.addAllowedOrigin("*");
        //放行哪些原始请求头部信息
        corsConfiguration.addAllowedHeader("*");
        //放行哪些请求方式
        corsConfiguration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //2. 添加映射路径
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}
