package com.cloume.maps.auth.dao;

import com.cloume.maps.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xcai
 * @version 1.0
 * @date 2021/03/15/21:20
 * @description
 */
public interface RoleMapper extends JpaRepository<Role, Integer> {
}
