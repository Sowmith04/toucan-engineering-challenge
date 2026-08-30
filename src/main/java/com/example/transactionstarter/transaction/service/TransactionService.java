package com.example.transactionstarter.transaction.service;

import com.example.transactionstarter.transaction.exception.TransactionNotFoundException;
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
            throw new IllegalArgumentException("Transaction ID is required");
        }

        if (repository.existsById(transaction.getTransactionId())) {
            throw new IllegalArgumentException( "Transaction ID already exists");
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

    public Transaction getTransaction(String transactionId){

        return repository.findById(transactionId)
           .orElseThrow(()->
                new TransactionNotFoundException("Transaction Not Found!"));
    }

    public Transaction updateStatus(String transactionId, TransactionStatus newStatus){

        if (newStatus == null) {
        throw new IllegalArgumentException("Transaction status is required");
    }

        Transaction transaction=repository.findById(transactionId)
            .orElseThrow(()->
                new TransactionNotFoundException("Transaction Not Found!"));

        TransactionStatus currentStatus=transaction.getTransactionStatus();

        if(currentStatus!=TransactionStatus.PENDING){
            throw new IllegalArgumentException("Status cannot be changed");
        }

        if(newStatus==TransactionStatus.PENDING){
            throw new IllegalArgumentException("Invalid Status Transition");
        }

        transaction.setTransactionStatus(newStatus);

        return repository.save(transaction);

    }

    public List<Transaction> getCustomerTransactions(String customerId){
        return repository.findByCustomerId(customerId);
    }

}
