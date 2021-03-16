package com.cloume.maps.commons.model.base;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xcai
 * @date 2021-03-10
 * @description 基础类，所有实体类都将继承此类
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 全局主键-id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @CreatedDate
    private LocalDateTime createTime = LocalDateTime.now();

    @LastModifiedDate
    private LocalDateTime updateTime = LocalDateTime.now();

    /**
     * 是否被移除
     */
    @JsonIgnore
    private Boolean removed = false;
}
