package com.cloume.maps.auth.dao;

import com.cloume.maps.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMapper extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
