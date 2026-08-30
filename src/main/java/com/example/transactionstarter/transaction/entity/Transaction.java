package com.example.transactionstarter.transaction.entity;

import com.example.transactionstarter.transaction.enums.TransactionType;
import com.example.transactionstarter.transaction.enums.TransactionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name="transactions")
public class Transaction {
    @Id
    @NotBlank
    private String transactionId;

    @NotBlank
    private String customerId;

    @NotNull
    @Positive
    private Double amount;

    @NotBlank
    private String currency;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    public Transaction(){

    }

    public void setTransactionId(String transactionId){
        this.transactionId=transactionId;
    }

    public String getTransactionId(){
        return transactionId;
    }

    public void setCustomerid(String customerId){
        this.customerId=customerId;
    }

    public String getCustomerId(){
        return customerId;
    }

    public void setAmount(Double amount){
        this.amount=amount;
    }

    public Double getAmount(){
        return amount;
    }

    public void setCurrency(String currency){
        this.currency=currency;
    }

    public String getCurrency(){
        return currency;
    }

    public void setTransactionType(TransactionType transactionType){
        this.transactionType=transactionType;
    }

    public TransactionType getTransactionType(){
        return transactionType;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
    
    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

}
