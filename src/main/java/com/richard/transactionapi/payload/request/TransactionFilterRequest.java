package com.richard.transactionapi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionFilterRequest {
    private String status;
    private String accountNumber;
    private LocalDate startDate;
    private LocalDate endDate;
}
