package com.cloume.maps.auth.config;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.cloume.commons.rest.response.RestResponse;
import com.cloume.maps.auth.filter.CustomClientCredentialsTokenEndpointFilter;
import com.cloume.maps.auth.model.User;
import com.cloume.maps.auth.service.impl.UserDetailsServiceImpl;
import com.cloume.maps.commons.constant.AuthConstants;
import com.cloume.maps.commons.enums.ResultCode;
import com.cloume.maps.commons.utils.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 授权服务配置
 */
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 配置客户端详情(数据库)
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //inMemory是存储到内存中 并未到数据库
        clients.inMemory()
                .withClient("maps")
                .secret(passwordEncoder.encode("123456"))
                .scopes("all")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials")
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(86400);
    }

    /**
     * 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(tokenEnhancer());
        delegates.add(accessTokenConverter());
        //配置JWT的内容增强器
        enhancerChain.setTokenEnhancers(delegates);
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(enhancerChain);
    }

    /**
     * 配置令牌端点安全约束
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        //认证服务器配置添加自定义过滤器
        CustomClientCredentialsTokenEndpointFilter endpointFilter = new CustomClientCredentialsTokenEndpointFilter(security);
        endpointFilter.afterPropertiesSet();
        endpointFilter.setAuthenticationEntryPoint(authenticationEntryPoint());
        security.addTokenEndpointAuthenticationFilter(endpointFilter);

//        .allowFormAuthenticationForClients()
        security.authenticationEntryPoint(authenticationEntryPoint())
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()");
    }

    /**
     * 自定义认证异常响应数据
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            response.setStatus(HttpStatus.HTTP_OK);
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            RestResponse result = ResponseUtil.bad(ResultCode.CLIENT_AUTHENTICATION_FAILED, e.getMessage());
            response.getWriter().print(JSONUtil.toJsonStr(result));
            response.getWriter().flush();
        };
    }

    /**
     * 使用非对称加密算法对token签名
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * 从classpath下的密钥库中获取密钥对(公钥+私钥)
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("maps.jks"), "123456".toCharArray());
        return keyStoreKeyFactory.getKeyPair("maps", "123456".toCharArray());
    }

    /**
     * JWT内容增强器
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> map = new HashMap<>(8);
            User user = (User) authentication.getUserAuthentication().getPrincipal();
            map.put(AuthConstants.USER_ID_KEY, user.getId());
            map.put(AuthConstants.CLIENT_ID_KEY, user.getClientId());
            map.put(AuthConstants.USER_NAME_KEY, user.getUsername());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
            return accessToken;
        };
    }
}
