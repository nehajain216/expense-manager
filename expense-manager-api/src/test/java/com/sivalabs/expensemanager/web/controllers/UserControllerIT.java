package com.sivalabs.expensemanager.web.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sivalabs.expensemanager.common.AbstractIntegrationTest;
import com.sivalabs.expensemanager.entities.User;
import com.sivalabs.expensemanager.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserControllerIT extends AbstractIntegrationTest {

    @Autowired private UserRepository userRepository;

    private List<User> userList = null;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        userList = new ArrayList<>();
        this.userList.add(new User(1L, "user1@gmail.com", "pwd1", "User1"));
        this.userList.add(new User(2L, "user2@gmail.com", "pwd2", "User2"));
        this.userList.add(new User(3L, "user3@gmail.com", "pwd3", "User3"));

        userList = userRepository.saveAll(userList);
    }

    @Test
    void shouldFetchAllUsers() throws Exception {
        this.mockMvc
                .perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(userList.size())));
    }
}
