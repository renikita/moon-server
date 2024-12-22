package com.development.moon.dev.repository;

import com.development.moon.dev.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findByLogin(@Param("login") String login);
}
