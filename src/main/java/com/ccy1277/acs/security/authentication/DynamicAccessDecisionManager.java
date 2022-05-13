package com.ccy1277.acs.security.authentication;

import cn.hutool.core.collection.CollUtil;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * 动态权限决策管理器: 判断用户是否有权限 实现权限校验
 * created by ccy on 2022/5/12
 */
public class DynamicAccessDecisionManager implements AccessDecisionManager {
    /**
     * 对于没有配置资源的接口我们直接允许访问，对于配置了资源的接口，我们把访问所需资源和用户拥有的资源进行比对，如果匹配则允许访问
     * @param authentication 在认证过程中封装的对象，可以从中取出封装的user对象
     * @param o 保护的安全对象，实际类型根据AbstractSecurityInterceptor实现的不同而不同，通常为 FilterInvocation或MethodInvocation
     * @param collection  针对该安全对象操作所需的权限，通常是通过SecurityMetadataSource的实体类获取 其实就是配置好的访问当前接口所需要的权限
     * @throws AccessDeniedException 认证不足
     * @throws InsufficientAuthenticationException 权限不足
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection)
            throws AccessDeniedException, InsufficientAuthenticationException {
        // 接口未被配置资源时 直接放行
        if(CollUtil.isEmpty(collection)){
            return;
        }
        /*
            简化一下逻辑
            当前请求所需的所有ConfigAttribute传递给AccessDecisionVoter进行决策，
            只要任一与用户拥有GrantedAuthority匹配，即代表授予访问权限;
            注意: 需要保证UserDetailsService中授予的角色权限标识
            和SecurityMetaDataSource中组织的请求需要的角色权限标识一致即可
         */
        Iterator<ConfigAttribute> configAttributeIterator = collection.iterator();
        while(configAttributeIterator.hasNext()){
            ConfigAttribute configAttribute = configAttributeIterator.next();
            String needAuthority = configAttribute.getAttribute();
            for(GrantedAuthority grantedAuthority : authentication.getAuthorities()){
                // 将访问所需资源与用户拥有资源对比
                if(needAuthority.trim().equals(grantedAuthority.getAuthority())){
                    return;
                }
            }
        }
        throw new AccessDeniedException("抱歉，您没有访问权限!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
