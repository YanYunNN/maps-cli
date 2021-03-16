package com.cloume.maps.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.cloume.maps.auth.dao.PermissionMapper;
import com.cloume.maps.auth.dao.RolePermissionMapper;
import com.cloume.maps.auth.dao.UserMapper;
import com.cloume.maps.auth.dao.UserRoleMapper;
import com.cloume.maps.auth.model.Permission;
import com.cloume.maps.auth.model.RolePermission;
import com.cloume.maps.auth.model.User;
import com.cloume.maps.auth.model.UserRole;
import com.cloume.maps.auth.service.UserService;
import com.cloume.maps.auth.util.RequestUtils;
import com.cloume.maps.commons.constant.AuthConstants;
import com.cloume.maps.commons.enums.ResultCode;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xcai
 * @desc 自定义用户认证和授权
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, UserService {
    private final UserRoleMapper userRoleMapper;
    private final UserMapper userMapper;
    private final PermissionMapper permissionMapper;
    private final RolePermissionMapper rolePermissionMapper;

    @Override
    public User getUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        }

        //!!!注入权限，网关资源认证要用
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<Integer> roleIds = userRoleMapper.getAllByUserId(user.getId())
                .stream().map(UserRole::getRoleId)
                .collect(Collectors.toList());
        user.setRoleIds(roleIds);
        roleIds.forEach(roleId -> authorities.add(new SimpleGrantedAuthority(String.valueOf(roleId))));
        user.setAuthorities(authorities);

        String clientId = RequestUtils.getAuthClientId();
        user.setClientId(clientId);
        if (!user.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!user.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        }
        return user;
    }


    /**
     * 网关资源服务器需要获取对应角色权限
     * @return Set<String>
     */
    @Override
    public Set<String> getAuthorityMatchPath(String path) {
        PathMatcher pathMatcher = new AntPathMatcher();
        Map<String, Set<String>> permissionRoles = permissionRoles();
        return permissionRoles.keySet().stream()
                .filter(key -> pathMatcher.match(key, path))
                .map(permissionRoles::get)
                .findFirst().orElse(new HashSet<>());
    }

    /**
     * 获取所有rolePermission
     * @return Map<String, Set < String>>
     */
    @Override
    public Map<String, Set<String>> permissionRoles() {
        List<Permission> permissions = permissionMapper.getAllByTypeAndRemovedFalse(1);
        Map<String, Set<String>> permissionRoles = new TreeMap<>();
        Optional.ofNullable(permissions).orElse(new ArrayList<>()).forEach(permission -> {

            // 转换 roleId -> ROLE_{roleId}
            Set<String> roles = Optional
                    .ofNullable(rolePermissionMapper.findAllByPermissionIdAndRemovedFalse(permission.getId()))
                    .orElse(new ArrayList<>())
                    .stream()
                    .map(RolePermission::getRoleId)
                    .map(roleId -> AuthConstants.AUTHORITY_PREFIX + roleId)
                    .collect(Collectors.toSet());

            if (CollUtil.isNotEmpty(roles)) {
                permissionRoles.put(permission.getMethod() + "_" + permission.getPerm(), roles);
            }
        });
        return permissionRoles;
    }
}
