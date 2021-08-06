package com.sivalabs.expensemanager.controllers;

import com.sivalabs.expensemanager.entities.Transaction;
import com.sivalabs.expensemanager.entities.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionControllerIT extends AbstractIntegrationTest {

    @Test
    public void shouldSaveTransactionSuccessfully() throws Exception {
        Transaction transaction = new Transaction(null, TransactionType.INCOME,250.0,
            "data for IT", LocalDate.now(),1);

        this.mockMvc.perform(post("/api/transactions")
        .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transaction)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.amount", is(transaction.getAmount())))
            .andExpect(jsonPath("$.txnType", is("INCOME")))
            .andExpect(jsonPath("$.description", is(transaction.getDescription())));

    }
}
