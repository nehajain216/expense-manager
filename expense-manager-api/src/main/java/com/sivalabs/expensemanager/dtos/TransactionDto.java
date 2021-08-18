package com.sivalabs.expensemanager.dtos;

import com.sivalabs.expensemanager.entities.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType txnType;

    @NotNull private Double amount;
    private String description;
    private LocalDate createdOn;
    private Integer createdBy;

    public static TransactionDto fromEntity(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setTxnType(transaction.getTxnType());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setDescription(transaction.getDescription());
        transactionDto.setCreatedOn(transaction.getCreatedOn());
        transactionDto.setCreatedBy(transaction.getCreatedBy());
        return transactionDto;
    }

    public static List<TransactionDto> fromEntity(List<Transaction> transactionList) {
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            transactionDtoList.add(TransactionDto.fromEntity(transaction));
        }
        return transactionDtoList;
    }

    public Transaction toEntity() {
        Transaction transaction = new Transaction();
        transaction.setId(this.getId());
        transaction.setTxnType(this.getTxnType());
        transaction.setAmount(this.getAmount());
        transaction.setDescription(this.getDescription());
        transaction.setCreatedOn(this.getCreatedOn());
        transaction.setCreatedBy(this.getCreatedBy());
        return transaction;
    }
}
