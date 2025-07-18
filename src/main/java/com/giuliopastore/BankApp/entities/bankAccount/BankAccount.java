package com.giuliopastore.BankApp.entities.bankAccount;

import com.giuliopastore.BankApp.entities.listener.GenerateUidEntityListener;
import com.giuliopastore.BankApp.entities.user.User;
import com.giuliopastore.BankApp.enums.SubscriptionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "BANK_ACCOUNT")
@EntityListeners(GenerateUidEntityListener.class)
@ToString
public class BankAccount {

    @Id
    @Column(length = 36, columnDefinition = "CHAR(36)")
    @NotNull
    private String uid;

    @NotNull
    private String iban;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private SubscriptionType subscriptionType;

    @ManyToOne
    @JoinColumn(name = "user_uid", nullable = false)
    private User user;
}
