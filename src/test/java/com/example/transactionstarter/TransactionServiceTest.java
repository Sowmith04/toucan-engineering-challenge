package com.example.transactionstarter;

import com.example.transactionstarter.transaction.entity.Transaction;
import com.example.transactionstarter.transaction.enums.TransactionType;
import com.example.transactionstarter.transaction.exception.TransactionNotFoundException;
import com.example.transactionstarter.transaction.enums.TransactionStatus;
import com.example.transactionstarter.transaction.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionServiceTest {
    
    @Autowired TransactionService service;

    @Test
    void createTransactionSuccess(){
        Transaction transaction =new Transaction();

        transaction.setTransactionId("TXN100");
        transaction.setCustomerId("CUST100");
        transaction.setAmount(1000.00);
        transaction.setCurrency("INR");
        transaction.setTransactionType(TransactionType.DEPOSIT);

        Transaction saved = service.createTransaction(transaction);

        assertNotNull(saved);
        assertEquals("TXN100",saved.getTransactionId());

    }

    @Test
    void duplicateTransactionRejected(){
        Transaction transaction = new Transaction();

        transaction.setTransactionId("TXN101");
        transaction.setCustomerId("CUST100");
        transaction.setAmount(200.00);
        transaction.setCurrency("USD");
        transaction.setTransactionType(TransactionType.WITHDRAW);

        service.createTransaction(transaction);

        assertThrows(
            IllegalArgumentException.class,
            ()->service.createTransaction(transaction)
        );


    }

    @Test
    void invalidTransactionRejected(){

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TXN103");
        transaction.setCustomerId("CUST103");
        transaction.setAmount(-100.0);
        transaction.setCurrency("INR");
        transaction.setTransactionType(TransactionType.DEPOSIT);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.createTransaction(transaction)
        );
    }

    @Test
    void transactionNotFound() {

        assertThrows(
                TransactionNotFoundException.class,
                () -> service.getTransaction("INVALID")
        );
    }

    @Test
    void updateStatusSuccess() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TXN102");
        transaction.setCustomerId("CUST102");
        transaction.setAmount(500.0);
        transaction.setCurrency("INR");
        transaction.setTransactionType(TransactionType.DEPOSIT);

        service.createTransaction(transaction);

        Transaction updated = service.updateStatus("TXN102",TransactionStatus.COMPLETED);

        assertEquals(TransactionStatus.COMPLETED, updated.getTransactionStatus());
    }
}
