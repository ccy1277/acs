package com.ccy1277.acs.config;

import com.ccy1277.acs.common.config.SwaggerConfig;
import com.ccy1277.acs.common.entity.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * Swagger配置扩展类
 * created by ccy on 2022/5/8
 */
@Configuration
@EnableOpenApi
public class AcsSwaggerConfig extends SwaggerConfig {
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.ccy1277.acs.sys")
                .title("ACS权限控制系统")
                .description("ACS权限控制系统API文档")
                .contactName("ccy1277")
                .contactUrl("/")
                .contactEmail("ccy1277@136.com")
                .version("1.0.0-SNAPSHOT")
                .enableSecurity(true)
                .build();
    }
}
