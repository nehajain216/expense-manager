package com.sivalabs.expensemanager.web.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.sivalabs.expensemanager.dtos.TransactionDto;
import com.sivalabs.expensemanager.entities.TransactionType;
import com.sivalabs.expensemanager.exceptions.TransactionNotFoundException;
import com.sivalabs.expensemanager.services.TransactionService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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
    void successfullySaveTransactionOnValidData() throws Exception {
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
    void failToSaveTransactionOnInvalidData() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"amount\": 250.00,\n"
                                                + "    \"description\": \"sample data\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldDeleteTransactionOnValidTransactionId() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/transactions/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingTransaction() throws Exception {
        doThrow(TransactionNotFoundException.class).when(transactionService).deleteTransaction(1L);
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/transactions/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldReturnListOfTransactions() throws Exception {
        TransactionDto transactionDto1 =
                new TransactionDto(1L, TransactionType.INCOME, 50.0, "", LocalDate.now(), 1);
        TransactionDto transactionDto2 =
                new TransactionDto(2L, TransactionType.INCOME, 50.0, "", LocalDate.now(), 1);
        List<TransactionDto> transactionDtoList = Arrays.asList(transactionDto1, transactionDto2);
        when(transactionService.viewAllTransaction()).thenReturn(transactionDtoList);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/transactions")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.size()", is(transactionDtoList.size())));
    }

    @Test
    void shouldReturnListOfTransactionsIfTransactionTypeMatches() throws Exception {
        TransactionDto transactionDto1 =
                new TransactionDto(1L, TransactionType.INCOME, 50.0, "", LocalDate.now(), 1);
        TransactionDto transactionDto2 =
                new TransactionDto(2L, TransactionType.INCOME, 50.0, "", LocalDate.now(), 1);
        List<TransactionDto> transactionDtoList = Arrays.asList(transactionDto1, transactionDto2);
        when(transactionService.searchTransactionByTxnType(TransactionType.INCOME))
                .thenReturn(transactionDtoList);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/transactions/search?txnType=INCOME")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.size()", is(transactionDtoList.size())));
    }

    @Test
    void shouldReturnListOfAllTransactionsIfTransactionIsAvailableBetweenGivenDate()
            throws Exception {
        TransactionDto transactionDto1 =
                new TransactionDto(1L, TransactionType.INCOME, 50.0, "", LocalDate.now(), 1);
        TransactionDto transactionDto2 =
                new TransactionDto(
                        2L, TransactionType.INCOME, 50.0, "", LocalDate.now().plusDays(1), 1);

        List<TransactionDto> transactionDtoList = Arrays.asList(transactionDto1, transactionDto2);
        when(transactionService.searchTransactionByDates(
                        LocalDate.now(), LocalDate.now().plusDays(1)))
                .thenReturn(transactionDtoList);
        mockMvc.perform(
                        MockMvcRequestBuilders.get(
                                        "/api/transactions/search/date-range?startDate={startDate}&endDate={endDate}",
                                        LocalDate.now(),
                                        LocalDate.now().plusDays(1))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.size()", is(transactionDtoList.size())));
    }
}
