package com.cloume.maps.auth.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author hxr
 * @date 2021-03-09
 */
@Data
@Entity
@Table(name = "auth_login_record")
public class LoginRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String description;

    private String clientIP;

    private LocalDateTime elapsedTime;

    private String message;

    private String token;

    private String username;

    private String loginTime;

    private String region;

}
