package com.richard.transactionapi.service;

import com.richard.transactionapi.exception.TransactionException;
import com.richard.transactionapi.model.Account;
import com.richard.transactionapi.payload.request.TransactionRequest;
import com.richard.transactionapi.payload.response.TransactionResponse;
import com.richard.transactionapi.repository.AccountRepository;
import com.richard.transactionapi.repository.TransactionRepository;
import com.richard.transactionapi.utils.AccountUtils;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionServiceTest {

    @InjectMocks
    private TransactionServiceImpl transactionService = new TransactionServiceImpl();

    @Mock
    private TransactionProcessor transactionProcessor;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        // Initialize all @Mocks and @InjectMocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTransferMoney_success() throws TransactionException {
        // Given
        TransactionRequest transactionDto = TransactionRequest.builder()
                .sourceAccountNumber("1234567890")
                .destinationAccountNumber("9876543210")
                .amount(BigDecimal.valueOf(100))
                .description("Test transfer")
                .build();

        Account sourceAccount = Account.builder()
                .accountNumber("1234567890")
                .accountBalance(BigDecimal.valueOf(1000))
                .build();

        Account destinationAccount = Account.builder()
                .accountNumber("9876543210")
                .accountBalance(BigDecimal.valueOf(500))
                .build();

        when(accountRepository.existsByAccountNumber(transactionDto.getDestinationAccountNumber())).thenReturn(true);
        when(accountRepository.findAccountByAccountNumber(transactionDto.getSourceAccountNumber())).thenReturn(sourceAccount);
        when(accountRepository.findAccountByAccountNumber(transactionDto.getDestinationAccountNumber())).thenReturn(destinationAccount);
        when(transactionProcessor.calculateTransactionFee(transactionDto.getAmount())).thenReturn(BigDecimal.valueOf(5));
        when(transactionProcessor.calculateBilledAmount(transactionDto.getAmount(), BigDecimal.valueOf(5))).thenReturn(BigDecimal.valueOf(105));

        // When
        TransactionResponse response = transactionService.transferMoney(transactionDto);

        // Then
        assertEquals(AccountUtils.TRANSACTION_SUCCESS_CODE, response.getResponseCode());
        assertEquals(AccountUtils.TRANSACTION_SUCCESS_MESSAGE, response.getResponseMessage());
        assertNotNull(response.getTransactionInfo());
    }

    @Test
    public void testTransferMoney_insufficientBalance() throws TransactionException {
        // Given
        TransactionRequest transactionDto = TransactionRequest.builder()
                .sourceAccountNumber("1234567890")
                .destinationAccountNumber("9876543210")
                .amount(BigDecimal.valueOf(1000))
                .description("Test transfer")
                .build();

        Account sourceAccount = Account.builder()
                .accountNumber("1234567890")
                .accountBalance(BigDecimal.valueOf(500))
                .build();

//        when(accountRepository.existsByAccountNumber(transactionDto.getDestinationAccountNumber())).thenReturn(true);
//        when(accountRepository.findAccountByAccountNumber(transactionDto.getSourceAccountNumber())).thenReturn(sourceAccount);

        // When
        TransactionResponse response = transactionService.transferMoney(transactionDto);

        // Then
        assertEquals(AccountUtils.INSUFFICIENT_BALANCE_CODE, response.getResponseCode());
        assertEquals(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE, response.getResponseMessage());
        assertNull(response.getTransactionInfo());
    }

}
