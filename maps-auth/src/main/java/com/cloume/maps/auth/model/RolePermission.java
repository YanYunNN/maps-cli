package com.cloume.maps.auth.model;

import com.cloume.maps.commons.model.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "auth_role_permission")
public class RolePermission extends BaseEntity {
    private Integer roleId;
    private Integer permissionId;
}
