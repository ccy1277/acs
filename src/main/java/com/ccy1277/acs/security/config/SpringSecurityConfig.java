package com.ccy1277.acs.security.config;

import com.ccy1277.acs.common.utils.JwtTokenUtil;
import com.ccy1277.acs.security.authentication.AuthAccessDeniedHandler;
import com.ccy1277.acs.security.authentication.AuthEntryPoint;
import com.ccy1277.acs.security.authentication.JwtAuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SpringSecurity 基础配置类
 * created by ccy on 2022/5/9
 */
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 配置springSecurity相关信息 用于配置需要拦截的url路径、jwt过滤器及出异常后的处理器
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry
                = http.authorizeRequests();
        // 直接放行的资源 不需要权限校验
        for (String url : ignoreUrlsConfig().getUrls()) {
            registry.antMatchers(url).permitAll();
        }
        // 允许跨域请求的options请求
        registry.antMatchers(HttpMethod.OPTIONS).permitAll();
        // 基于JWT
        registry.and()
                //关闭跨站请求防护
                .csrf()
                .disable()
                // 不通过Session获取SecurityContext
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 任何请求都要身份认证
                .authorizeRequests()
                .anyRequest()
                // 当前用户不是anonymous时返回true 要求身份认证
                .authenticated()
                // 自定义权限拒绝处理类 自定义未授权和未登录结果返回
                .and()
                .exceptionHandling()
                .accessDeniedHandler(authAccessDeniedHandler())
                .authenticationEntryPoint(authEntryPoint())
                // 自定义权限拦截器 JWT过滤器
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 用于配置UserDetailsService及PasswordEncoder
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /**
     * 通过AuthenticationManager的authenticate方法来进行用户认证
     */
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * PasswordEncoder：SpringSecurity定义的用于对密码进行编码及比对的接口，目前使用的是BCryptPasswordEncode
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置登录授权过滤器:在用户名和密码校验前添加的过滤器，如果有jwt的token，会自行根据token信息进行登录
     */
    @Bean
    public JwtAuthTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthTokenFilter();
    }

    /**
     * 没有权限访问时
     */
    @Bean
    public AuthAccessDeniedHandler authAccessDeniedHandler(){
        return new AuthAccessDeniedHandler();
    }

    /**
     * 当未登录或者token失效访问接口时
     */
    @Bean
    public AuthEntryPoint authEntryPoint(){
        return new AuthEntryPoint();
    }

    @Bean
    public IgnoreUrlsConfig ignoreUrlsConfig() {
        return new IgnoreUrlsConfig();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }
}
