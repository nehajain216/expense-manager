package com.sivalabs.expensemanager.controllers;

import com.sivalabs.expensemanager.entities.Transaction;
import com.sivalabs.expensemanager.entities.TransactionType;
import com.sivalabs.expensemanager.services.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;

@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerTest {

    @MockBean
    TransactionService transactionService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void SuccessfullySaveTransactionOnValidData() throws Exception {
        Transaction transaction = new Transaction(1, TransactionType.INCOME,50.0,"", LocalDate.now(),1);
        when(transactionService.saveTransaction(any(Transaction.class))).thenReturn(transaction);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions")
        .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"txnType\": \"INCOME\",\n" +
                "    \"amount\": 250.00,\n" +
                "    \"description\": \"sample data\"}"))
            .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.txnType",is("INCOME")));
    }

    @Test
    public void FailToSaveTransactionOnInvalidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"amount\": 250.00,\n" +
            "    \"description\": \"sample data\"}"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
