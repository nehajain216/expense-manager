package com.sivalabs.expensemanager.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import com.sivalabs.expensemanager.dtos.TransactionDto;
import com.sivalabs.expensemanager.entities.Transaction;
import com.sivalabs.expensemanager.entities.TransactionType;
import com.sivalabs.expensemanager.repositories.TransactionRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TransactionServiceTest {

    TransactionRepository transactionRepository;

    @Test
    void shouldSaveTransactionSuccessfullyOnValidData() {
        Transaction transaction =
                new Transaction(1L, TransactionType.INCOME, 50.0, "", LocalDate.now(), 1);
        transactionRepository = Mockito.mock(TransactionRepository.class);
        Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionService transactionService = new TransactionService(transactionRepository);
        TransactionDto data = transactionService.saveTransaction(new TransactionDto());
        assertEquals(TransactionType.INCOME, data.getTxnType());
    }
}
