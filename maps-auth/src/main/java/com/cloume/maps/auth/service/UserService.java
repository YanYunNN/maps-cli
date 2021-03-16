package com.cloume.maps.auth.service;


import com.cloume.maps.auth.model.User;

import java.util.Map;
import java.util.Set;


public interface UserService {

    User getUserByUsername(String username);

    Set<String> getAuthorityMatchPath(String path);

    Map<String, Set<String>> permissionRoles();
}
