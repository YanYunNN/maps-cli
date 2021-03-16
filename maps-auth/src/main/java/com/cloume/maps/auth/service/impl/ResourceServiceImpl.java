package com.cloume.maps.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author xcai
 * @version 1.0
 * @date 2021/03/11/12:00
 * @description 用于编写业务初始化用户角色权限
 */
/*@Service
public class ResourceServiceImpl {

    private Map<String, List<String>> resourceRolesMap;

    @PostConstruct
    public void initData() {
        resourceRolesMap = new TreeMap<>();
        resourceRolesMap.put("/api/hello", CollUtil.toList("ADMIN"));
        resourceRolesMap.put("/api/user/currentUser", CollUtil.toList("ADMIN", "TEST"));
    }
}*/
