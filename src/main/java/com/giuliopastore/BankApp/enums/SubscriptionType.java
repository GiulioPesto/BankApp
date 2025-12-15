package com.giuliopastore.BankApp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SubscriptionType {
    FREE("FREE"),
    SILVER("SILVER"),
    GOLD("GOLD");

    private final String value;
}
