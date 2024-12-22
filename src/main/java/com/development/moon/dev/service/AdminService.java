package com.development.moon.dev.service;

import com.development.moon.dev.model.Admin;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {

    Admin save(Admin admin);

    Admin findById(Integer id);

    public List<Admin> findAll();

    boolean deleteById(Integer id);

    Admin findByReg(String login);

}
