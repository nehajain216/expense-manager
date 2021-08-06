package com.sivalabs.expensemanager.repositories;

import com.sivalabs.expensemanager.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {}
