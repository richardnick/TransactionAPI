package com.richard.transactionapi.utils;

import java.time.Year;

public class AccountUtils {

    public static String generateAccountNumber(){

        /**
         * The account Number will be a concatenation of year and randomSixDigits
         */
        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;
        int randomNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);

        String year = String.valueOf(currentYear);
        String newRandomNumber = String.valueOf(randomNumber);
        StringBuilder accountNumber = new StringBuilder();

        return accountNumber.append(year).append(newRandomNumber).toString();

    }

    public static String generateTransactionReference() {
        return java.util.UUID.randomUUID().toString();
    }

    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "This email already has an account";
    public static final String ACCOUNT_CREATION_SUCCESS_CODE = "002";
    public static final String ACCOUNT_CREATION_SUCCESS_MESSAGE = "Account was successfully created";
    public static final String TRANSACTION_SUCCESS_CODE = "004";
    public static final String TRANSACTION_SUCCESS_MESSAGE = "Transaction was Successful";

    public static final String INSUFFICIENT_BALANCE_CODE = "005";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "INSUFFICIENT_BALANCE";

    public static final String TRANSACTION_FAILED_CODE = "006";
    public static final String TRANSACTION_FAILED_MESSAGE = "Transaction Failed";

    public static final String ACCOUNT_CREDITED_CODE = "003";
    public static final String ACCOUNT_CREDITED_MESSAGE = "Account credited successfully ";






}
