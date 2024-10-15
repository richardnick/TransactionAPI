package com.richard.transactionapi.payload.response;

import com.richard.transactionapi.model.TransactionStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {

    private String responseCode;
    private String responseMessage;
    private TransactionInfo transactionInfo;
}
