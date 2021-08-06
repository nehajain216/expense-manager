package com.sivalabs.expensemanager.web.controllers;

import com.sivalabs.expensemanager.entities.User;
import com.sivalabs.expensemanager.services.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/api/users")
    public List<User> viewAllUsers() {
        return userService.getAllUsers();
    }
}
