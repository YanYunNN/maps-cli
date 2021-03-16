package com.cloume.maps.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cloume.maps.commons.constant.AuthConstants;
import com.nimbusds.jose.JWSObject;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;

/**
 * 将登录用户的JWT转化成用户信息的全局过滤器
 */
@Component
@Log4j2
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 无token放行
        String token = exchange.getRequest().getHeaders().getFirst(AuthConstants.AUTHORIZATION_KEY);
        if (StrUtil.isBlank(token) || !token.startsWith(AuthConstants.AUTHORIZATION_PREFIX)) {
            return chain.filter(exchange);
        }

        try {
            //从token中解析用户信息并设置到Header中去
            String realToken = token.replace(AuthConstants.AUTHORIZATION_PREFIX, Strings.EMPTY);
            JWSObject jwsObject = JWSObject.parse(realToken);
            String payload = jwsObject.getPayload().toString();
            JSONObject jsonObject = JSONUtil.parseObj(payload);
            String jti = jsonObject.getStr(AuthConstants.JWT_JTI);
            log.info("AuthGlobalFilter.filter() user:{}", payload);
            ServerHttpRequest request = exchange.getRequest().mutate().header(AuthConstants.JWT_PAYLOAD_KEY, payload).build();
            exchange = exchange.mutate().request(request).build();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
