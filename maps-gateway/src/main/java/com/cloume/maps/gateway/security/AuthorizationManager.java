package com.cloume.maps.gateway.security;

import cn.hutool.core.util.StrUtil;
import com.cloume.maps.commons.constant.AuthConstants;
import com.cloume.maps.gateway.service.UserRemote;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * @author xcai
 * @version 1.0
 * @date 2021/03/09/19:18
 * @description
 */
@Log4j2
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Autowired
    private UserRemote userRemote;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        String path = request.getMethodValue() + "_" + request.getURI().getPath();
        if (!"GET_/favicon.ico".equals(path)) {
            log.info("请求，path={}", path);
        }

        // 1.对应跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 2.token为空拒绝访问
        String token = request.getHeaders().getFirst(AuthConstants.AUTHORIZATION_KEY);
        if (StrUtil.isBlank(token)) {
            if (!"GET_/favicon.ico".equals(path)) {
                log.info("请求token为空拒绝访问，path={}", path);
            }
            return Mono.just(new AuthorizationDecision(false));
        }

        // 3.获取当前路径可访问角色列表
        Set<String> authorities = userRemote.getAuthority(path);

        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(role -> {
                    // 4. roleId是请求用户的角色(格式:ROLE_{roleId})，authorities是请求资源所需要角色的集合
                    log.info("访问路径：{}", path);
                    log.info("用户角色roleId：{}", role);
                    log.info("资源需要权限authorities：{}", authorities);
                    return authorities.contains(role);
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
