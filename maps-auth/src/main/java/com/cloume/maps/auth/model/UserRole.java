package com.cloume.maps.auth.model;

import com.cloume.maps.commons.model.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "auth_user_role")
public class UserRole extends BaseEntity {
    private Integer userId;
    private Integer roleId;
}
