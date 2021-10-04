package com.example.springbootrest.service;


import com.example.springbootrest.dao.RoleDao;
import com.example.springbootrest.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDao roleDao;

    @Transactional
    @Override
    public boolean add(Role role) {
        Role existedRole = roleDao.findByRole(role.getRole());

        if (existedRole != null){
            return false;
        }
        roleDao.saveRole(role);
        return true;
    }

    @Override
    public Role getById(Long id) {
        return null;
    }

    @Override
    public Role getByName(String name) {
        return roleDao.findByRole(name);
    }

    @Override
    public boolean update(Role role) {
        return false;
    }

    @Override
    public Set<Role> findAll() {
        return roleDao.findAll();
    }
}
