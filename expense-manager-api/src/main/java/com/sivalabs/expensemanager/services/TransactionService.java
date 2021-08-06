package com.sivalabs.expensemanager.services;

import com.sivalabs.expensemanager.dtos.TransactionDto;
import com.sivalabs.expensemanager.entities.Transaction;
import com.sivalabs.expensemanager.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionDto saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = transactionRepository.save(transactionDto.toEntity());
        return transactionDto.fromEntity(transaction);
    }
}
