package com.cloume.maps.auth.model;

import com.cloume.maps.commons.model.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "auth_permission")
public class Permission extends BaseEntity {

    private String name;
    private String perm;
    private Long moduleId;
    private String method;
    private Integer type;

    /**
     * 拥有资源权限角色ID集合
     */
    @Transient
    private List<Long> roleIds;
}
