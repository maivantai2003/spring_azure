package com.nhom27.nhatkykhambenh.repository;

import com.nhom27.nhatkykhambenh.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepo extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
