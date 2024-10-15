package com.richard.transactionapi.service;

import com.richard.transactionapi.exception.TransactionException;
import com.richard.transactionapi.model.Transaction;
import com.richard.transactionapi.payload.request.TransactionFilterRequest;
import com.richard.transactionapi.payload.request.TransactionRequest;
import com.richard.transactionapi.payload.response.TransactionResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ITransactionService {

    public TransactionResponse transferMoney(TransactionRequest transactionDto) throws TransactionException;;
    public List<Transaction> getTransactions(String accountNumber, String startDate, String endDate);
}
