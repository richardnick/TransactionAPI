package com.richard.transactionapi.service;

import com.richard.transactionapi.payload.request.AccountRequest;
import com.richard.transactionapi.payload.request.CreditAccountRequest;
import com.richard.transactionapi.payload.response.AccountResponse;

public interface IAccountService {

    AccountResponse createAccount(AccountRequest accountRequest);
    AccountResponse creditAccount(CreditAccountRequest request);
}
