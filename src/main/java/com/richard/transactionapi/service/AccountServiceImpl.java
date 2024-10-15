package com.richard.transactionapi.service;

import com.richard.transactionapi.exception.AccountNotFoundException;
import com.richard.transactionapi.model.Account;
import com.richard.transactionapi.payload.request.AccountRequest;
import com.richard.transactionapi.payload.request.CreditAccountRequest;
import com.richard.transactionapi.payload.response.AccountInfo;
import com.richard.transactionapi.payload.response.AccountResponse;
import com.richard.transactionapi.repository.AccountRepository;
import com.richard.transactionapi.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements IAccountService{

    @Autowired
    private AccountRepository accountRepo;
    @Override
    public AccountResponse createAccount(AccountRequest accountRequest) {

        /**
         * Create an account and save in db
         * Check if account already exists using the email.
         */
        if(accountRepo.existsByEmail(accountRequest.getEmail())){
            return AccountResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        Account newAccount = Account.builder()
                .accountName(accountRequest.getAccountName())
                .email(accountRequest.getEmail())
                .phoneNumber(accountRequest.getPhoneNumber())
                .accountBalance(BigDecimal.ZERO)
                .accountNumber(AccountUtils.generateAccountNumber())
                .build();
        Account savedAccount = accountRepo.save(newAccount);
        return AccountResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedAccount.getAccountBalance())
                        .accountName(savedAccount.getAccountName())
                        .accountNumber(savedAccount.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public AccountResponse creditAccount(CreditAccountRequest request) {
        boolean isAccountExist = accountRepo.existsByAccountNumber(request.getAccountNumber());
        if(!isAccountExist){
            throw new AccountNotFoundException(request.getAccountNumber());
        }
        Account accountToCredit = accountRepo.findAccountByAccountNumber(request.getAccountNumber());
        accountToCredit.setAccountBalance(accountToCredit.getAccountBalance().add(request.getAmount()));
        return AccountResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_MESSAGE  )
                .accountInfo(AccountInfo.builder()
                        .accountName(accountToCredit.getAccountName())
                        .accountNumber(accountToCredit.getAccountNumber())
                        .accountBalance(accountToCredit.getAccountBalance())
                        .build())
                .build();
    }
}
