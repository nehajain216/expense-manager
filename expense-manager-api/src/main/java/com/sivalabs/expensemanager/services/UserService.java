package com.sivalabs.expensemanager.services;

import com.sivalabs.expensemanager.entities.User;
import com.sivalabs.expensemanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }
}

