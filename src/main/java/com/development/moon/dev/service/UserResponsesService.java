package com.development.moon.dev.service;


import com.development.moon.dev.model.UserResponses;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserResponsesService {

    UserResponses save(UserResponses ur);

    UserResponses findById(Integer id);

    public List<UserResponses> findAll();

    boolean deleteById(Integer id);
}
