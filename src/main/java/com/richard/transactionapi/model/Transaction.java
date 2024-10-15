package com.richard.transactionapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "transaction_table")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;
    private BigDecimal amount;
    private BigDecimal transactionFee;
    private BigDecimal billedAmount;
    private String description;
    private LocalDate dateCreated;
    private TransactionStatus status;
    private String statusMessage;
    private boolean commissionWorthy;
    private BigDecimal commission;
    private String senderAccountNumber;
    private String recipientAccountNumber;
}
