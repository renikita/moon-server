package com.development.moon.dev.service;

import com.development.moon.dev.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    Role save(Role role);

    Role findById(Integer id);

    public List<Role> findAll();

    boolean deleteById(Integer id);
}
