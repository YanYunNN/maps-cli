package com.cloume.maps.auth.controller;

import com.cloume.commons.rest.response.RestResponse;
import com.cloume.maps.auth.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/oauth")
@AllArgsConstructor
@Slf4j
@Api(tags = "认证模块")
public class AuthController {

    private final TokenEndpoint tokenEndpoint;
    private final UserService userService;


    @ApiOperation(value = "OAuth2认证", notes = "login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", defaultValue = "password", value = "授权模式", required = true),
            @ApiImplicitParam(name = "client_id", defaultValue = "client", value = "Oauth2客户端ID", required = true),
            @ApiImplicitParam(name = "client_secret", defaultValue = "123456", value = "Oauth2客户端秘钥", required = true),
            @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
            @ApiImplicitParam(name = "username", defaultValue = "admin", value = "登录用户名"),
            @ApiImplicitParam(name = "password", defaultValue = "123456", value = "登录密码"),
            // 微信小程序认证参数（无小程序可忽略）
            @ApiImplicitParam(name = "code", value = "小程序code"),
            @ApiImplicitParam(name = "encryptedData", value = "包括敏感数据在内的完整用户信息的加密数据"),
            @ApiImplicitParam(name = "iv", value = "加密算法的初始向量")
    })
    @PostMapping("/token")
    public OAuth2AccessToken postAccessToken(Principal principal, @RequestParam Map<String, String> parameters)
            throws HttpRequestMethodNotSupportedException {
        return tokenEndpoint.postAccessToken(principal, parameters).getBody();
    }

    /**
     * 网关资源服务器根据 path 取得权限
     * @return Set<String>
     */
    @GetMapping("/authority")
    public Set<String> getAuthority(@RequestParam String path) {
        return userService.getAuthorityMatchPath(path);
    }

    /**
     * 获取当前登录权限
     * @param principal
     * @return
     */
    @GetMapping("/principal")
    public RestResponse<?> getPrincipal(Principal principal) {
        return RestResponse.good(principal);
    }
}
