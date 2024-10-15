package com.richard.transactionapi.controller;

import com.richard.transactionapi.exception.TransactionException;
import com.richard.transactionapi.model.Transaction;
import com.richard.transactionapi.payload.request.TransactionRequest;
import com.richard.transactionapi.payload.response.TransactionResponse;
import com.richard.transactionapi.service.ITransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping("/transferProcess")
    public ResponseEntity<TransactionResponse> process(@RequestBody TransactionRequest request) throws TransactionException {
        TransactionResponse response = transactionService.transferMoney(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Endpoint 2: Retrieve List of Transactions
     */
    @GetMapping()
    public ResponseEntity<List<Transaction>> getTransactions(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String accountNumber,
            @RequestParam(required = false) String  startDate,
            @RequestParam(required = false) String  endDate) {
        List<Transaction> transactions = transactionService.getTransactions(accountNumber, startDate, endDate);
        return new  ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
