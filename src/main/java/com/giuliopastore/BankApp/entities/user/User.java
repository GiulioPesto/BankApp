package com.giuliopastore.BankApp.entities.user;

import com.giuliopastore.BankApp.entities.bankAccount.BankAccount;
import com.giuliopastore.BankApp.entities.listener.GenerateUidEntityListener;
import com.giuliopastore.BankApp.enums.SubscriptionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "USER")
@EntityListeners(GenerateUidEntityListener.class)
public class User {

    @Id
    @Column(length = 36, columnDefinition = "CHAR(36)")
    @NotNull
    private String uid;

    @NotNull
    private String name;

    private String secondName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String address;

    @NotNull
    private String phoneNumber;

    @Column(name = "subscription_type")
    private SubscriptionType subscriptionType;

    @NotNull
    private Integer zipCode;

    @NotNull
    private String taxIdCode;

    @NotNull
    @Min(value = 18, message = "Age must be greater than 18")
    private Integer age;

    @OneToMany(mappedBy = "user")
    private List<BankAccount>  bankAccounts;

    @Override
    public String toString() {
        return "{\n" +
                "  \"uid\": \"" + uid + "\",\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"secondName\": \"" + secondName + "\",\n" +
                "  \"lastName\": \"" + lastName + "\",\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"password\": \"" + password + "\",\n" +
                "  \"address\": \"" + address + "\",\n" +
                "  \"phoneNumber\": \"" + phoneNumber + "\",\n" +
                "  \"zipCode\": " + zipCode + ",\n" +
                "  \"taxIdCode\": \"" + taxIdCode + "\",\n" +
                "  \"age\": " + age + "\n" +
                "}";
    }
}