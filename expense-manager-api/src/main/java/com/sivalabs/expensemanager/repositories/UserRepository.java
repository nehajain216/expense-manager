package com.sivalabs.expensemanager.repositories;

import com.sivalabs.expensemanager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
