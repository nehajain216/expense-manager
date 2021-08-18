package com.sivalabs.expensemanager.web.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sivalabs.expensemanager.common.AbstractIntegrationTest;
import com.sivalabs.expensemanager.dtos.TransactionDto;
import com.sivalabs.expensemanager.entities.Transaction;
import com.sivalabs.expensemanager.entities.TransactionType;
import com.sivalabs.expensemanager.repositories.TransactionRepository;
import com.sivalabs.expensemanager.services.TransactionService;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

class TransactionControllerIT extends AbstractIntegrationTest {

    @Autowired private TransactionService transactionService;
    @Autowired private TransactionRepository transactionRepository;
    Transaction transaction;

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();
        transaction =
                new Transaction(
                        null, TransactionType.INCOME, 2500.0, "data for IT", LocalDate.now(), 1);

        transactionRepository.save(transaction);
    }

    @Test
    void shouldSaveTransactionSuccessfully() throws Exception {
        TransactionDto transactionDto =
                new TransactionDto(
                        null, TransactionType.INCOME, 250.0, "data for IT", LocalDate.now(), 1);

        this.mockMvc
                .perform(
                        post("/api/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(transactionDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount", is(transactionDto.getAmount())))
                .andExpect(jsonPath("$.txnType", is(TransactionType.INCOME.name())))
                .andExpect(jsonPath("$.description", is(transactionDto.getDescription())));
    }

    @Test
    void shouldDeleteTransactionSuccessfully() throws Exception {
        this.mockMvc
                .perform(
                        delete("/api/transactions/{id}", transaction.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnListOfTransactions() throws Exception {
        this.mockMvc
                .perform(get("/api/transactions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
