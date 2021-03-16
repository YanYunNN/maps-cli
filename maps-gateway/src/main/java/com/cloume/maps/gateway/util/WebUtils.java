package com.cloume.maps.gateway.util;

import cn.hutool.json.JSONUtil;
import com.cloume.maps.commons.enums.ResultCode;
import com.cloume.maps.commons.utils.ResponseUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @Author xcai
 * @Date 2021-03-16 13:30
 * @Version 1.0.0
 */
public class WebUtils {
    public static Mono writeFailedToResponse(ServerHttpResponse response, ResultCode resultCode) {
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set("Access-Control-Allow-Origin", "*");
        response.getHeaders().set("Cache-Control", "no-cache");
        String body = JSONUtil.toJsonStr(ResponseUtil.bad(resultCode));
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer))
                .doOnError(error -> DataBufferUtils.release(buffer));
    }
}
