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
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class TransactionControllerIT extends AbstractIntegrationTest {

    @Autowired private TransactionService transactionService;
    @Autowired private TransactionRepository transactionRepository;
    List<Transaction> transactionList = null;

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();
        Transaction transaction1 =
                new Transaction(
                        null, TransactionType.INCOME, 150.0, "", LocalDate.now().minusDays(1), 1);
        Transaction transaction2 =
                new Transaction(null, TransactionType.INCOME, 250.0, "", LocalDate.now(), 1);
        Transaction transaction3 =
                new Transaction(
                        null, TransactionType.INCOME, 350.0, "", LocalDate.now().plusDays(1), 1);
        Transaction transaction4 =
                new Transaction(
                        null, TransactionType.EXPENSE, 450.0, "", LocalDate.now().plusDays(2), 1);

        transactionList =
                transactionRepository.saveAll(
                        List.of(transaction1, transaction2, transaction3, transaction4));
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
                        delete("/api/transactions/{id}", transactionList.get(0).getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnListOfTransactions() throws Exception {
        this.mockMvc
                .perform(get("/api/transactions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(transactionList.size())));
    }

    @Test
    void shouldReturnListOfTransactionsIfTransactionTypeMatches() throws Exception {
        this.mockMvc
                .perform(
                        get("/api/transactions/search?txnType=INCOME")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(3)));
    }

    @Test
    void shouldReturnListOfAllTransactionsIfTransactionIsAvailableBetweenGivenDate()
            throws Exception {
        this.mockMvc
                .perform(
                        get(
                                        "/api/transactions/search/date-range?startDate={startDate}&endDate={endDate}",
                                        LocalDate.now(),
                                        LocalDate.now().plusDays(1))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(2)));
    }
}
