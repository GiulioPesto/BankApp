package com.giuliopastore.BankApp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SubscriptionType {
    FREE("FREE"),
    STANDARD("STANDARD"),
    PREMIUM("PREMIUM");

    private final String value;
}
