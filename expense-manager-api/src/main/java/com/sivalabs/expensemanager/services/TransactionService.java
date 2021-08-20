package com.sivalabs.expensemanager.services;

import com.sivalabs.expensemanager.dtos.TransactionDto;
import com.sivalabs.expensemanager.entities.Transaction;
import com.sivalabs.expensemanager.entities.TransactionType;
import com.sivalabs.expensemanager.exceptions.TransactionNotFoundException;
import com.sivalabs.expensemanager.repositories.TransactionRepository;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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
        return TransactionDto.fromEntity(transaction);
    }

    public void deleteTransaction(long id) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isEmpty()) {
            throw new TransactionNotFoundException("No transaction is found with id " + id);
        }
        transactionRepository.deleteById(id);
    }

    public List<TransactionDto> viewAllTransaction() {
        List<Transaction> transactionList = transactionRepository.findAll();
        return TransactionDto.fromEntity(transactionList);
    }

    public List<TransactionDto> searchTransaction(String txnType) {
        TransactionType transactionType = TransactionType.valueOf(txnType.toUpperCase());
        List<Transaction> bytxnType = transactionRepository.findBytxnType(transactionType);
        return TransactionDto.fromEntity(bytxnType);
    }
}
