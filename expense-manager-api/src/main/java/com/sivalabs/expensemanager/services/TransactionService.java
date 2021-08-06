package com.sivalabs.expensemanager.services;

import com.sivalabs.expensemanager.entities.Transaction;
import com.sivalabs.expensemanager.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public Transaction saveTransaction(Transaction transaction)
    {
        return transactionRepository.save(transaction);
    }
}
