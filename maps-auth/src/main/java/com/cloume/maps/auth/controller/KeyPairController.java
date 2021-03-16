package com.cloume.maps.auth.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * @author xcai
 * @version 1.0
 * @date 2021/03/09/19:08
 * @description RSA公钥开放接口
 */
@Api(tags = "公钥对模块")
@RestController
@AllArgsConstructor
public class KeyPairController {

    @Autowired
    private KeyPair keyPair;

    @GetMapping("/rsa/publicKey")
    public Map<String, Object> getPublicKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}