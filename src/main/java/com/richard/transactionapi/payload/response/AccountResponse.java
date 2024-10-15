package com.richard.transactionapi.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {

    private String responseCode;
    private String responseMessage;
    private AccountInfo accountInfo;
}
