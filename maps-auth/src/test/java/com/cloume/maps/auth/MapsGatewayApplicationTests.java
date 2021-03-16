package com.cloume.maps.auth;

import com.cloume.maps.auth.dao.RoleMapper;
import com.cloume.maps.auth.dao.UserMapper;
import com.cloume.maps.auth.dao.UserRoleMapper;
import com.cloume.maps.auth.model.Role;
import com.cloume.maps.auth.model.User;
import com.cloume.maps.auth.model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class MapsGatewayApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    void addUser() {
        User build = User.builder()
                .clientId("client")
                .username("test")
                .password(passwordEncoder.encode("123456"))
                .name("test")
                .department("测试")
                .enabled(true)
                .build();
        User save = userMapper.save(build);
    }

    @Test
    void addUserRole() {
        UserRole userRole = new UserRole();
        userRole.setRoleId(1);
        userRole.setUserId(1);
        userRole = userRoleMapper.save(userRole);
    }
    @Test
    void addRole() {
        Role role = new Role();
        role.setBanned(false);
        role.setName("ROLE_NORMAL");
        role.setNickname("普通用户");
        role.setComment("普通用户/匿名用户");
        role = roleMapper.save(role);
    }

}
