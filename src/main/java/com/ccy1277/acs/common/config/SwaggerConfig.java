package com.ccy1277.acs.common.config;

import com.ccy1277.acs.common.properties.SwaggerProperties;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;

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
                .securityContexts(Arrays.asList(securityContext()))
                // ApiKey的name需与SecurityReference的reference保持一致
                .securitySchemes(Arrays.asList(new ApiKey("Authorization", "Bearer", SecurityScheme.In.HEADER.name())))
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

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return singletonList(
                new SecurityReference("Authorization", authorizationScopes));
    }

    /**
     * 自定义Swagger配置
     */
    public abstract SwaggerProperties swaggerProperties();
}
