package com.cloume.maps.gateway.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xcai
 * @version 1.0
 * @date 2021/03/15/12:00
 * @description 对外提供调用接口
 */
@FeignClient(value = "maps-auth", fallback = UserRemoteFallBack.class)
public interface UserRemote {
    @GetMapping(value = "/oauth/authority")
    Set<String> getAuthority(@RequestParam String path);
}
