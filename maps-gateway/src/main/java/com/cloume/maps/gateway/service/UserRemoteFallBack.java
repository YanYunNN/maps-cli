package com.cloume.maps.gateway.service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xcai
 * @version 1.0
 * @date 2021/03/15/20:22
 * @description
 */
public class UserRemoteFallBack implements UserRemote {

    @Override
    public Set<String> getAuthority(String path) {
        return new HashSet<>();
    }
}
