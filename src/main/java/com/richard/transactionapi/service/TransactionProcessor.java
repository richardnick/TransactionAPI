package com.richard.transactionapi.service;

import com.richard.transactionapi.exception.TransactionException;
import com.richard.transactionapi.model.Transaction;
import com.richard.transactionapi.model.TransactionStatus;
import com.richard.transactionapi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class  TransactionProcessor {

    @Autowired
    private TransactionRepository transactionRepo;

    public void acceptTransfer(Long transactionId) throws TransactionException {
        Transaction transaction = transactionRepo.findById(transactionId).orElseThrow();
        if (transaction.getStatus() == TransactionStatus.PENDING) {
            transaction.setStatus(TransactionStatus.SUCCESSFUL);
            transaction.setCommissionWorthy(true);
            transaction.setTransactionFee(calculateTransactionFee(transaction.getAmount()));
            transaction.setStatusMessage("Transaction Successful");
            transaction.setBilledAmount(calculateBilledAmount(transaction.getAmount(), transaction.getTransactionFee()));
            transactionRepo.save(transaction);
        } else {
            // throw exception if transaction is not in PENDING status
            throw new TransactionException("Transaction is not in PENDING status");
        }
    }


    public BigDecimal calculateTransactionFee(BigDecimal amount) {
        // calculate transaction fee (0.5% of the original amount with a cap of 100)
        BigDecimal transactionFee = amount.multiply(new BigDecimal("0.005"));
        transactionFee = transactionFee.min(new BigDecimal("100"));
        return transactionFee;
    }

    public BigDecimal calculateBilledAmount(BigDecimal amount, BigDecimal transactionFee) {
        // calculate billed amount (original amount + transaction fee)
        return amount.add(transactionFee);
    }

    private BigDecimal calculateCommission(BigDecimal amount) {
        BigDecimal transactionFee = amount.multiply(new BigDecimal("0.005")); // 0.5% of the original amount
        transactionFee = transactionFee.min(new BigDecimal("100")); // cap at 100
        BigDecimal commission = transactionFee.multiply(new BigDecimal("0.2")); // 20% of the transaction fee
        return commission;

    }

    @Scheduled(cron = "0 2 * * * *") // runs at 2 AM every day
    private void updateCommissions() {
        List<Transaction> successfulTransactions = transactionRepo.findByStatus(TransactionStatus.valueOf(String.valueOf(TransactionStatus.SUCCESSFUL)));
        for (Transaction transaction : successfulTransactions) {
            BigDecimal commission = calculateCommission(transaction.getAmount());
            transaction.setCommission(commission);
            transactionRepo.save(transaction);
        }
    }

}
