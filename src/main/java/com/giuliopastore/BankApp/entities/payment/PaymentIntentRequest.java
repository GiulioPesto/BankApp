package com.giuliopastore.BankApp.entities.payment;
import lombok.Data;
@Data
public class PaymentIntentRequest {
    private Long amount; // importo in centesimi (es. 1000 = 10.00 EUR)
    private String currency; // es. "eur", "usd"
    private String description;
}
