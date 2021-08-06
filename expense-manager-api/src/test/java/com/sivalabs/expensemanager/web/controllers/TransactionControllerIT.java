package com.sivalabs.expensemanager.web.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sivalabs.expensemanager.common.AbstractIntegrationTest;
import com.sivalabs.expensemanager.dtos.TransactionDto;
import com.sivalabs.expensemanager.entities.TransactionType;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class TransactionControllerIT extends AbstractIntegrationTest {

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
}
