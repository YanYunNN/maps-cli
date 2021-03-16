package com.cloume.maps.auth.model;

import com.cloume.maps.commons.model.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "auth_role")
public class Role extends BaseEntity {

    /**
     * 角色英文名，用于权限校验
     */
    private String name;

    /**
     * 角色中文名，用于显示
     */
    private String nickname;

    /**
     * 角色描述信息
     */
    private String comment;

    /**
     * 角色状态，是否已禁用
     */
    private Boolean banned = false;

    /**
     * Spring Security 4.0以上版本角色都默认以'ROLE_'开头
     * @param name
     */
    public void setName(String name) {
        if (!name.contains("ROLE_")) {
            this.name = "ROLE_" + name;
        } else {
            this.name = name;
        }
    }
}
