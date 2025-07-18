package com.giuliopastore.BankApp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransferStatus {
    SENT("SENT"),
    RECEIVED("RECEIVED"),
    REJECTED("REJECTED"),
    PENDING("PENDING");

    private final String value;
}
