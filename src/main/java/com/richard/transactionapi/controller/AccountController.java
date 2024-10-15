package com.richard.transactionapi.controller;

import com.richard.transactionapi.payload.request.AccountRequest;
import com.richard.transactionapi.payload.request.CreditAccountRequest;
import com.richard.transactionapi.payload.response.AccountResponse;
import com.richard.transactionapi.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@Tag(name = " Account Management API")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @Operation(
            summary = "Create A New Account ",
            description = "Creating a new Account for a User and assigning an account ID"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 created "
    )
    @PostMapping("/create")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest accountRequest){
        AccountResponse createdAccount = accountService.createAccount(accountRequest);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Credit An Account",
            description = "Credit an account with a given amount"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS "
    )
    @PostMapping("/credit")
    public ResponseEntity<AccountResponse> creditAccount(@RequestBody CreditAccountRequest request){
         AccountResponse creditedAccount = accountService.creditAccount(request);
         return new ResponseEntity<>(creditedAccount, HttpStatus.OK);
    }
}