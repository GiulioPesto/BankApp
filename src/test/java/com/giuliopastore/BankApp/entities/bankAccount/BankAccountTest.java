package com.giuliopastore.BankApp.entities.bankAccount;

import com.giuliopastore.BankApp.entities.user.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void testBankAccountCreation() {
        // Given
        User user = createTestUser();

        // When
        BankAccount account = new BankAccount();
        account.setUid("account-uid-123");
        account.setIban("IT60X0542811101000000123456");
        account.setBalance(new BigDecimal("1500.75"));
        account.setUser(user);

        // Then
        assertNotNull(account);
        assertEquals("account-uid-123", account.getUid());
        assertEquals("IT60X0542811101000000123456", account.getIban());
        assertEquals(new BigDecimal("1500.75"), account.getBalance());
        assertEquals(user, account.getUser());
    }

    @Test
    void testBankAccountAllArgsConstructor() {
        // Given
        User user = createTestUser();

        // When
        BankAccount account = new BankAccount(
                "account-uid-123",
                "IT60X0542811101000000123456",
                new BigDecimal("2500.00"),
                user
        );

        // Then
        assertNotNull(account);
        assertEquals("account-uid-123", account.getUid());
        assertEquals("IT60X0542811101000000123456", account.getIban());
        assertEquals(new BigDecimal("2500.00"), account.getBalance());
        assertEquals(user, account.getUser());
    }

    @Test
    void testBankAccountNoArgsConstructor() {
        // When
        BankAccount account = new BankAccount();

        // Then
        assertNotNull(account);
        assertNull(account.getUid());
        assertNull(account.getIban());
        assertNull(account.getBalance());
        assertNull(account.getUser());
    }

    @Test
    void testBankAccountGettersAndSetters() {
        // Given
        BankAccount account = new BankAccount();
        User user = createTestUser();

        // When
        account.setUid("uid-456");
        account.setIban("IT28W8000000292100645211222");
        account.setBalance(new BigDecimal("5000.00"));
        account.setUser(user);

        // Then
        assertEquals("uid-456", account.getUid());
        assertEquals("IT28W8000000292100645211222", account.getIban());
        assertEquals(new BigDecimal("5000.00"), account.getBalance());
        assertEquals(user, account.getUser());
    }

    @Test
    void testBankAccountToString() {
        // Given
        User user = createTestUser();
        BankAccount account = new BankAccount(
                "account-uid-123",
                "IT60X0542811101000000123456",
                new BigDecimal("1000.00"),
                user
        );

        // When
        String toString = account.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("account-uid-123"));
        assertTrue(toString.contains("IT60X0542811101000000123456"));
    }

    @Test
    void testBankAccountWithZeroBalance() {
        // Given
        User user = createTestUser();

        // When
        BankAccount account = new BankAccount();
        account.setUid("account-zero");
        account.setIban("IT60X0542811101000000123456");
        account.setBalance(BigDecimal.ZERO);
        account.setUser(user);

        // Then
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    void testBankAccountWithNegativeBalance() {
        // Given
        User user = createTestUser();

        // When
        BankAccount account = new BankAccount();
        account.setUid("account-negative");
        account.setIban("IT60X0542811101000000123456");
        account.setBalance(new BigDecimal("-100.00"));
        account.setUser(user);

        // Then
        assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) < 0);
    }

    private User createTestUser() {
        return User.builder()
                .uid("user-uid-123")
                .name("Mario")
                .lastName("Rossi")
                .email("mario.rossi@example.com")
                .password("password123")
                .address("Via Roma 123")
                .phoneNumber("3331234567")
                .zipCode(20100)
                .taxIdCode("RSSMRA90E15H501A")
                .birthDate(LocalDate.of(1990, 5, 15))
                .birthPlace("Milano")
                .city("Milano")
                .province("MI")
                .authUserId("auth-123")
                .build();
    }
}

