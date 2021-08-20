package com.sivalabs.expensemanager.web.controllers;

import com.sivalabs.expensemanager.dtos.TransactionDto;
import com.sivalabs.expensemanager.services.TransactionService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/api/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDto saveTransaction(@Valid @RequestBody TransactionDto transactionDto) {
        return transactionService.saveTransaction(transactionDto);
    }

    @GetMapping("/api/transactions")
    public List<TransactionDto> viewAllTransaction() {
        return transactionService.viewAllTransaction();
    }

    @DeleteMapping("/api/transactions/{id}")
    public void deleteTransaction(@PathVariable long id) {
        transactionService.deleteTransaction(id);
    }

    @GetMapping("/api/transactions/search/{txnType}")
    public List<TransactionDto> searchTransaction(@PathVariable String txnType)
    {
        return transactionService.searchTransaction(txnType);
    }
}
