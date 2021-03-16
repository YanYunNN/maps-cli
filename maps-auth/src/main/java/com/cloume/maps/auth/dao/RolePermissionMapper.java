package com.cloume.maps.auth.dao;

import com.cloume.maps.auth.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xcai
 * @version 1.0
 * @date 2021/03/15/21:11
 * @description
 */
public interface RolePermissionMapper extends JpaRepository<RolePermission, Integer> {
    List<RolePermission> findAllByPermissionIdAndRemovedFalse(int pms_id);
}
