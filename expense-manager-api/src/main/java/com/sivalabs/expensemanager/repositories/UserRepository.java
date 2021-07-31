package com.sivalabs.expensemanager.repositories;

import com.sivalabs.expensemanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
}
