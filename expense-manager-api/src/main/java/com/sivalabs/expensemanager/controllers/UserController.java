package com.sivalabs.expensemanager.controllers;

import com.sivalabs.expensemanager.entities.User;
import com.sivalabs.expensemanager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/api/users")
    public List<User> viewAllUsers()
    {
        return userService.getAllUsers();
    }
}
