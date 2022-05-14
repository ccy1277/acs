package com.ccy1277.acs.security.authentication;

import com.ccy1277.acs.security.config.IgnoreUrlsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 动态权限过滤器: 实现基于路径的动态权限过滤
 * created by ccy on 2022/5/12
 */
public class DynamicAuthenticatedFilter extends AbstractSecurityInterceptor implements Filter {
    private final static Logger LOGGER = LoggerFactory.getLogger(DynamicAuthenticatedFilter.class);
    //白名单
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    // 配置自定义的SecurityMetadataSource
    @Autowired
    private DynamicSecurityMetaDataSource dynamicSecurityMetaDataSource;

    // 配置自定义的AccessDecisionManager
    @Autowired
    public void setMyAccessDecisionManager(DynamicAccessDecisionManager dynamicAccessDecisionManager) {
        super.setAccessDecisionManager(dynamicAccessDecisionManager);
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return dynamicSecurityMetaDataSource;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // SpringSecurity封装doFilter传进来的request,response和FilterChain对象保存起来，供FilterSecurityInterceptor的处理代码调用
        FilterInvocation filterInvocation = new FilterInvocation(servletRequest, servletResponse, filterChain);
        // 放行options请求（否则前端调用会出现跨域问题）
        if(request.getMethod().equals(HttpMethod.OPTIONS.toString())){
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
            return;
        }
        // 白名单请求
        PathMatcher pathMatcher = new AntPathMatcher();
        for(String path : ignoreUrlsConfig.getUrls()){
            // 匹配除去host（域名或者ip）部分的路径
            if(pathMatcher.match("/acs" + path, request.getRequestURI())){
                filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
                return;
            }
        }
        LOGGER.info("DynamicAuthenticated resource: {}", request.getRequestURI());
        // 此处会调用AccessDecisionManager中的decide方法进行鉴权操作
        InterceptorStatusToken interceptorStatusToken = super.beforeInvocation(filterInvocation);
        try {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } finally {
            super.afterInvocation(interceptorStatusToken, null);
        }
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void destroy() {

    }
}
