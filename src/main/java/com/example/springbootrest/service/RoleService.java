package com.example.springbootrest.service;

import com.example.springbootrest.model.Role;

import java.util.Set;

public interface RoleService {
    boolean add(Role role);
    Role getById(Long id);
    Role getByName(String name);
    boolean update(Role role);
    Set<Role> findAll();
}
