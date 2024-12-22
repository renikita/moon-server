package com.development.moon.dev.usercase;

import com.development.moon.dev.model.Role;
import com.development.moon.dev.repository.AdminRepository;
import com.development.moon.dev.repository.RoleRepository;
import com.development.moon.dev.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServicedb implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findById(Integer id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public boolean deleteById(Integer id) { roleRepository.deleteById(id); return adminRepository.existsById(id); }
}
