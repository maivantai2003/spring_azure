package com.nhom27.nhatkykhambenh.service.implementation;

import com.nhom27.nhatkykhambenh.model.Role;
import com.nhom27.nhatkykhambenh.repository.IRoleRepo;
import com.nhom27.nhatkykhambenh.service.interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private IRoleRepo roleRepo;

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepo.findByName(roleName);
    }
}
