package com.sivalabs.expensemanager.services;

import com.sivalabs.expensemanager.entities.Transaction;
import com.sivalabs.expensemanager.entities.TransactionType;
import com.sivalabs.expensemanager.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class TransactionServiceTest {

    @Test
    public void shouldSaveTransactionSuccessfullyOnValidData()
    {
        Transaction transaction = new Transaction(1, TransactionType.INCOME,50.0,"", LocalDate.now(),1);
        TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
        Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionService transactionService = new TransactionService(transactionRepository);
        Transaction data = transactionService.saveTransaction(transaction);
        assertEquals(TransactionType.INCOME,data.getTxnType());
    }

}
