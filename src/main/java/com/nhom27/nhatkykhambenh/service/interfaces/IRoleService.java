package com.nhom27.nhatkykhambenh.service.interfaces;

import com.nhom27.nhatkykhambenh.model.Role;

public interface IRoleService {
    Role findByRoleName(String roleName);
}
