package com.example.transactionstarter.transaction.service;

import com.example.transactionstarter.transaction.entity.Transaction;
import com.example.transactionstarter.transaction.enums.TransactionStatus;
import com.example.transactionstarter.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository){
        this.repository=repository;
    }

    public Transaction createTransaction(Transaction transaction){

        if(transaction.getTransactionId()==null || transaction.getTransactionId().isBlank()){
            throw new IllegalArgumentException("Transaction Id is required");
        }

        if (transaction.getCustomerId() == null || transaction.getCustomerId().isBlank()) {
            throw new IllegalArgumentException("Customer ID is required");
        }

        if (transaction.getAmount() == null || transaction.getAmount() <= 0) {
            throw new IllegalArgumentException( "Amount must be greater than zero");
        }

        if (transaction.getCurrency() == null || transaction.getCurrency().isBlank()) {
            throw new IllegalArgumentException( "Currency is required");
        }

        List<String> allowedCurrencies = List.of("INR", "USD", "EUR");

        if (!allowedCurrencies.contains(transaction.getCurrency())) {
            throw new IllegalArgumentException( "Invalid currency");
        }

        if (transaction.getTransactionType() == null) {
            throw new IllegalArgumentException("Transaction type is required");
        }

        transaction.setTransactionStatus(TransactionStatus.PENDING);

        return repository.save(transaction);
    }

}
