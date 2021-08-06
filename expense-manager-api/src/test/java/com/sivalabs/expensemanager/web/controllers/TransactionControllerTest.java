package com.sivalabs.expensemanager.web.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.sivalabs.expensemanager.dtos.TransactionDto;
import com.sivalabs.expensemanager.entities.TransactionType;
import com.sivalabs.expensemanager.services.TransactionService;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = TransactionController.class)
class TransactionControllerTest {

    @MockBean TransactionService transactionService;

    @Autowired MockMvc mockMvc;

    @Test
    void SuccessfullySaveTransactionOnValidData() throws Exception {
        TransactionDto transactionDto =
                new TransactionDto(1L, TransactionType.INCOME, 50.0, "", LocalDate.now(), 1);
        when(transactionService.saveTransaction(any(TransactionDto.class)))
                .thenReturn(transactionDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{ \"txnType\": \"INCOME\",\n"
                                                + "    \"amount\": 250.00,\n"
                                                + "    \"description\": \"sample data\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(
                        MockMvcResultMatchers.jsonPath(
                                "$.txnType", is(TransactionType.INCOME.name())));
    }

    @Test
    void FailToSaveTransactionOnInvalidData() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"amount\": 250.00,\n"
                                                + "    \"description\": \"sample data\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
