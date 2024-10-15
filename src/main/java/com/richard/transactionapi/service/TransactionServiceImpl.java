package com.richard.transactionapi.service;

import com.richard.transactionapi.exception.AccountNotFoundException;
import com.richard.transactionapi.exception.TransactionException;
import com.richard.transactionapi.model.Account;
import com.richard.transactionapi.model.Transaction;
import com.richard.transactionapi.model.TransactionStatus;
import com.richard.transactionapi.payload.request.TransactionRequest;
import com.richard.transactionapi.payload.response.TransactionInfo;
import com.richard.transactionapi.payload.response.TransactionResponse;
import com.richard.transactionapi.repository.AccountRepository;
import com.richard.transactionapi.repository.TransactionRepository;
import com.richard.transactionapi.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepo;
    @Autowired
    private TransactionProcessor acceptTransferService;

    @Autowired
    private AccountRepository accountRepo;

    @Override
    public TransactionResponse transferMoney(TransactionRequest transactionDto) throws TransactionException {

        boolean isDestinationAccountExist = accountRepo.existsByAccountNumber(transactionDto.getDestinationAccountNumber());
        if(!isDestinationAccountExist){
            throw new AccountNotFoundException(transactionDto.getDestinationAccountNumber());
        }

        Account soureAccount = accountRepo.findAccountByAccountNumber(transactionDto.getSourceAccountNumber());
        BigDecimal availableBalance = soureAccount.getAccountBalance();
        if(availableBalance.compareTo(transactionDto.getAmount()) < 0 ){
            return TransactionResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .transactionInfo(null)
                    .build();
        }

        BigDecimal fee = acceptTransferService.calculateTransactionFee(transactionDto.getAmount());
        BigDecimal billAmount = acceptTransferService.calculateBilledAmount(transactionDto.getAmount(), fee);
        soureAccount.setAccountBalance(soureAccount.getAccountBalance().subtract(billAmount));
        accountRepo.save(soureAccount);
        Transaction transaction = Transaction.builder()
                .reference(AccountUtils.generateTransactionReference())
                .amount(transactionDto.getAmount())
                .description(transactionDto.getDescription())
                .dateCreated(LocalDate.now())
                .status(TransactionStatus.PENDING)
                .statusMessage("Transaction is Pending")
                .senderAccountNumber(transactionDto.getSourceAccountNumber())
                .recipientAccountNumber(transactionDto.getDestinationAccountNumber())
                .build();

        transactionRepo.save(transaction);

        Account destinationAccount = accountRepo.findAccountByAccountNumber(transaction.getRecipientAccountNumber());
        destinationAccount.setAccountBalance(destinationAccount.getAccountBalance().add(transaction.getAmount()));
            acceptTransferService.acceptTransfer(transaction.getId());

            TransactionResponse response = TransactionResponse.builder()
                    .responseCode(AccountUtils.TRANSACTION_SUCCESS_CODE)
                    .responseMessage(AccountUtils.TRANSACTION_SUCCESS_MESSAGE)
                    .transactionInfo(TransactionInfo.builder()
                            .transactionId(transaction.getId())
                            .senderAccountNumber(transaction.getSenderAccountNumber())
                            .recipientAccountNumber(transaction.getRecipientAccountNumber())
                            .amount(transaction.getAmount())
                            .status(transaction.getStatus())
                            .build())
                    .build();


        return response;

    }


    /**
     * Scheduled operation to generate transaction summaries.
     * Runs daily at a specified time (e.g., midnight).
     */
    @Scheduled(cron = "0 1 * * * *") // Runs every day at 1 AM
    public void generateDailyTransactionSummary() {
        // Specify the day (e.g., previous day)
        LocalDate summaryDate = LocalDate.now().minusDays(1);
        // Generate the summary
        List<Transaction> summary = generateTransactionSummary(summaryDate);
        // Print or log the summary (or save to file)
        System.out.println(summary.toString());

    }

    public List<Transaction> getTransactions( String accountNumber, String startDate, String  endDate) {

        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate,DateTimeFormatter.ISO_DATE);
        List<Transaction> transactionList = transactionRepo.findAll().stream()
                .filter(transaction -> transaction.getSenderAccountNumber().equals(accountNumber))
                .filter(transaction -> transaction.getDateCreated().isEqual(start))
                .filter(transaction -> transaction.getDateCreated().isEqual(end)).toList();
        return transactionList;
    }

    // Generate summary logic (retrieve transactions from service/repo)
    private List<Transaction> generateTransactionSummary(LocalDate date) {

        List<Transaction> transactionList = transactionRepo.findAll().stream()
                .filter(transaction -> transaction.getDateCreated().isEqual(date)).toList();
        return transactionList;
    }

}
