package com.ccy1277.acs.common.config;

import com.ccy1277.acs.common.entity.SwaggerProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger Api文档 基础配置类
 * created by ccy on 2022/5/8
 */
public abstract class SwaggerConfig {
    SwaggerProperties swaggerProperties = swaggerProperties();
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.OAS_30)
                .select()
                // 为当前包下controller生成API文档
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getApiBasePackage()))
                // 为有@Api注解的Controller生成API文档
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                // 为有@ApiOperation注解的Controller生成API文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // paths 指定生成API的path
                .paths(PathSelectors.any())
                .build()
                // 文档信息
                .apiInfo(apiInfo(swaggerProperties));
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties){
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
//                .termsOfServiceUrl("http://127.0.0.1:9090")
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()))
                .version(swaggerProperties.getVersion())
                .build();
    }

    /**
     * 自定义Swagger配置
     */
    public abstract SwaggerProperties swaggerProperties();
}
