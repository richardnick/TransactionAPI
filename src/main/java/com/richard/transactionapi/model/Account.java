package com.richard.transactionapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "account_table")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(
            name = "Account Name"
    )
    private String accountName;

    @Schema(
            name = "Account Number"
    )
    private String accountNumber;

    @Schema(
            name = "Account Balance"
    )
    private BigDecimal accountBalance;

    @Schema(
            name = "User email"
    )
    private String email;

    @Schema(
            name = "User PhoneNumber"
    )
    private String phoneNumber;

}
