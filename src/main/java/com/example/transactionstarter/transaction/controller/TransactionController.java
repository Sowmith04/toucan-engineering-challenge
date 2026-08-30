package com.example.transactionstarter.transaction.controller;

import com.example.transactionstarter.transaction.dto.StatusUpdateRequest;
import com.example.transactionstarter.transaction.entity.Transaction;
import com.example.transactionstarter.transaction.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService service){
        this.service=service;
    }

    @PostMapping
    public Transaction createTransaction(@Valid @RequestBody Transaction transaction){
        return service.createTransaction(transaction);
    }

    @GetMapping("/{id}")
    public Transaction getTransaction(@PathVariable String id){
        return service.getTransaction(id);
    }

    @PutMapping("/{id}/status")
    public Transaction updateStatus(@PathVariable String id, @RequestBody StatusUpdateRequest request){
        return service.updateStatus(id,request.getStatus());
    }

    
}
