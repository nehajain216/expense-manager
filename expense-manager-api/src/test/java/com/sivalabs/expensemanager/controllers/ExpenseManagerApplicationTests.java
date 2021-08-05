package com.sivalabs.expensemanager.controllers;

import com.sivalabs.expensemanager.entities.User;
import com.sivalabs.expensemanager.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;



class ExpenseManagerApplicationTests extends AbstractIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private List<User> userList = null;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        userList = new ArrayList<>();
        this.userList.add(new User(1, "user1@gmail.com", "pwd1","User1"));
        this.userList.add(new User(2, "user2@gmail.com", "pwd2","User2"));
        this.userList.add(new User(3, "user3@gmail.com", "pwd3","User3"));

        userList = userRepository.saveAll(userList);
    }

    @Test
    void shouldFetchAllUsers() throws Exception {
        this.mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", is(userList.size())));
    }

}
