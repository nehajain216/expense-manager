package com.sivalabs.expensemanager.controllers;

import com.sivalabs.expensemanager.entities.Transaction;
import com.sivalabs.expensemanager.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/api/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction saveTransaction(@Valid @RequestBody Transaction transaction)
    {
        return transactionService.saveTransaction(transaction);
    }

}
