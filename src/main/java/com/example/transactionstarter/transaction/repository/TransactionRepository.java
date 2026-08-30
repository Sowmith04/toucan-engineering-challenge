package com.example.transactionstarter.transaction.repository;

import com.example.transactionstarter.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    
}
