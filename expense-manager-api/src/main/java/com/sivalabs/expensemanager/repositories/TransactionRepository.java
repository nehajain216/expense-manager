package com.sivalabs.expensemanager.repositories;

import com.sivalabs.expensemanager.entities.Transaction;
import com.sivalabs.expensemanager.entities.TransactionType;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByTxnType(TransactionType transactionType);

    List<Transaction> findByCreatedOnBetween(LocalDate startDate, LocalDate endDate);
}
