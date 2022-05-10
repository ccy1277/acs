package com.ccy1277.acs.security.authentication;

import com.ccy1277.acs.common.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt登录授权器
 * created by ccy on 2022/5/9
 */
public class JwtAuthTokenFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthTokenFilter.class);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader(tokenHeader);

        if(authHeader != null && authHeader.startsWith(tokenHead)){
            // 去除令牌类型开头
            String token = authHeader.substring(tokenHead.length());
            String username = jwtTokenUtil.getUNameFromToken(token);
            LOGGER.info("checking user: {}", username);
            /*
            Spring Security使用一个Authentication对象来描述当前用户的相关信息,赋值给当前的SecurityContext;
            对应的SecurityContext不存在时，则Spring Security将为咱们创建一个空的SecurityContext并进行返回
             */
            if(null != username && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                LOGGER.info("{} Authorities: {}", userDetails.getUsername(), userDetails.getAuthorities());
                if(jwtTokenUtil.validateToken(token, userDetails)){
                    // 根据userDetails构建新的带用户名和密码以及权限的Authentication
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    // 设置authentication中details
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    LOGGER.info("Authenticated user: {}", username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
