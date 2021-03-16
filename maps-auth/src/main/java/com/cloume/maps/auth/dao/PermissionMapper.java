package com.cloume.maps.auth.dao;

import com.cloume.maps.auth.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionMapper extends JpaRepository<Permission, Integer> {
    List<Permission> getAllByTypeAndRemovedFalse(Integer type);
}
