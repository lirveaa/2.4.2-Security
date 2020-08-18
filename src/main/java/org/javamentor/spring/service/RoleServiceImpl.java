package org.javamentor.spring.service;

import org.javamentor.spring.model.Role;
import org.javamentor.spring.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDao roleService;

    public List<Role> rolesList() {
        return roleService.rolesList();
    }
}

