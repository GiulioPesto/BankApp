package com.giuliopastore.BankApp.entities.bankAccount;

import com.giuliopastore.BankApp.entities.listener.GenerateUidEntityListener;
import com.giuliopastore.BankApp.entities.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table( name = "bank_account")
@EntityListeners(GenerateUidEntityListener.class)
@ToString
public class BankAccount {

    @Id
    @Column(length = 36, columnDefinition = "VARCHAR(36)")
    @NotNull
    private String uid;

    @NotNull
    private String iban;

    @NotNull
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "user_uid", nullable = false)
    private User user;
}
