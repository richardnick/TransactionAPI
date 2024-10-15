package com.richard.transactionapi.payload.response;

import com.richard.transactionapi.model.TransactionStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionInfo {

    private Long transactionId;
    private String senderAccountNumber;
    private String recipientAccountNumber;
    private BigDecimal amount;
    private TransactionStatus status;
}
