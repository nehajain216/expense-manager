package com.sivalabs.expensemanager.services;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.sivalabs.expensemanager.dtos.TransactionDto;
import com.sivalabs.expensemanager.entities.Transaction;
import com.sivalabs.expensemanager.entities.TransactionType;
import com.sivalabs.expensemanager.exceptions.TransactionNotFoundException;
import com.sivalabs.expensemanager.repositories.TransactionRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TransactionServiceTest {

    TransactionRepository transactionRepository;
    TransactionService transactionService;
    Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new Transaction(1L, TransactionType.INCOME, 50.0, "", LocalDate.now(), 1);
        transactionRepository = Mockito.mock(TransactionRepository.class);
        transactionService = new TransactionService(transactionRepository);
    }

    @Test
    void shouldSaveTransactionSuccessfullyOnValidData() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        TransactionDto data = transactionService.saveTransaction(new TransactionDto());
        assertEquals(TransactionType.INCOME, data.getTxnType());
    }

    @Test
    void shouldDeleteTransactionOnValidTransactionId() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        transactionService.deleteTransaction(1);
        verify(transactionRepository).deleteById(transaction.getId());
    }

    @Test
    void shouldThrowExceptionOnInValidTransactionId() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(
                TransactionNotFoundException.class, () -> transactionService.deleteTransaction(1));
        verify(transactionRepository, never()).deleteById(any(Long.class));
        TransactionNotFoundException myException =
                assertThrows(
                        TransactionNotFoundException.class,
                        () -> transactionService.deleteTransaction(1L));
        assertTrue(myException.getMessage().contains("No transaction is found with id 1"));
    }

    @Test
    void shouldReturnListOfAllTransactions(){
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(transaction));
        List<TransactionDto> transactionDtos = transactionService.viewAllTransaction();
        assertEquals(1,transactionDtos.size());
    }

    @Test
    void shouldReturnEmptyListIfNoTransactionsAreAvailable(){
        when(transactionRepository.findAll()).thenReturn(Arrays.asList());
        List<TransactionDto> transactionDtos = transactionService.viewAllTransaction();
        assertEquals(0,transactionDtos.size());
    }
}
