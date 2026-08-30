package com.example.transactionstarter.transaction.dto;

import com.example.transactionstarter.transaction.enums.TransactionStatus;

public class StatusUpdateRequest {
    private TransactionStatus status;

    public void setStatus(TransactionStatus status){
        this.status=status;
    }

    public TransactionStatus getStatus(){
        return status;
    }
}
