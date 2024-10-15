package com.richard.transactionapi.payload.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequest {

    private String accountName;
    private String email;
    private String phoneNumber;
}
