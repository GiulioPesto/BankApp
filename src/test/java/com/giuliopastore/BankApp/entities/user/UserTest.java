package com.giuliopastore.BankApp.entities.user;

import com.giuliopastore.BankApp.entities.bankAccount.BankAccount;
import com.giuliopastore.BankApp.enums.SubscriptionType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserCreationWithBuilder() {
        // Given
        LocalDate birthDate = LocalDate.of(1990, 5, 15);

        // When
        User user = User.builder()
                .uid("test-uid-123")
                .name("Mario")
                .secondName("Giovanni")
                .lastName("Rossi")
                .email("mario.rossi@example.com")
                .password("password123")
                .address("Via Roma 123")
                .phoneNumber("3331234567")
                .subscriptionType(SubscriptionType.PREMIUM)
                .zipCode(20100)
                .taxIdCode("RSSMRA90E15H501A")
                .birthDate(birthDate)
                .birthPlace("Milano")
                .city("Milano")
                .province("MI")
                .authUserId("auth-user-123")
                .build();

        // Then
        assertNotNull(user);
        assertEquals("test-uid-123", user.getUid());
        assertEquals("Mario", user.getName());
        assertEquals("Giovanni", user.getSecondName());
        assertEquals("Rossi", user.getLastName());
        assertEquals("mario.rossi@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("Via Roma 123", user.getAddress());
        assertEquals("3331234567", user.getPhoneNumber());
        assertEquals(SubscriptionType.PREMIUM, user.getSubscriptionType());
        assertEquals(20100, user.getZipCode());
        assertEquals("RSSMRA90E15H501A", user.getTaxIdCode());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals("Milano", user.getBirthPlace());
        assertEquals("Milano", user.getCity());
        assertEquals("MI", user.getProvince());
        assertEquals("auth-user-123", user.getAuthUserId());
    }

    @Test
    void testUserWithBankAccounts() {
        // Given
        User user = User.builder()
                .uid("test-uid-123")
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

        BankAccount account1 = new BankAccount();
        account1.setUid("account-1");
        account1.setIban("IT60X0542811101000000123456");
        account1.setBalance(new BigDecimal("1000.00"));
        account1.setUser(user);

        BankAccount account2 = new BankAccount();
        account2.setUid("account-2");
        account2.setIban("IT60X0542811101000000654321");
        account2.setBalance(new BigDecimal("2000.00"));
        account2.setUser(user);

        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);

        // When
        user.setBankAccounts(accounts);

        // Then
        assertNotNull(user.getBankAccounts());
        assertEquals(2, user.getBankAccounts().size());
        assertEquals("account-1", user.getBankAccounts().get(0).getUid());
        assertEquals("account-2", user.getBankAccounts().get(1).getUid());
    }

    @Test
    void testUserGettersAndSetters() {
        // Given
        User user = new User();

        // When
        user.setUid("uid-123");
        user.setName("Luigi");
        user.setSecondName("Antonio");
        user.setLastName("Bianchi");
        user.setEmail("luigi.bianchi@example.com");
        user.setPassword("pass456");
        user.setAddress("Via Verdi 45");
        user.setPhoneNumber("3339876543");
        user.setSubscriptionType(SubscriptionType.STANDARD);
        user.setZipCode(10100);
        user.setTaxIdCode("BNCLGI85M10L219X");
        user.setBirthDate(LocalDate.of(1985, 8, 10));
        user.setBirthPlace("Torino");
        user.setCity("Torino");
        user.setProvince("TO");
        user.setAuthUserId("auth-456");

        // Then
        assertEquals("uid-123", user.getUid());
        assertEquals("Luigi", user.getName());
        assertEquals("Antonio", user.getSecondName());
        assertEquals("Bianchi", user.getLastName());
        assertEquals("luigi.bianchi@example.com", user.getEmail());
        assertEquals("pass456", user.getPassword());
        assertEquals("Via Verdi 45", user.getAddress());
        assertEquals("3339876543", user.getPhoneNumber());
        assertEquals(SubscriptionType.STANDARD, user.getSubscriptionType());
        assertEquals(10100, user.getZipCode());
        assertEquals("BNCLGI85M10L219X", user.getTaxIdCode());
        assertEquals(LocalDate.of(1985, 8, 10), user.getBirthDate());
        assertEquals("Torino", user.getBirthPlace());
        assertEquals("Torino", user.getCity());
        assertEquals("TO", user.getProvince());
        assertEquals("auth-456", user.getAuthUserId());
    }

    @Test
    void testUserToString() {
        // Given
        User user = User.builder()
                .uid("uid-123")
                .name("Mario")
                .secondName("Giovanni")
                .lastName("Rossi")
                .email("mario.rossi@example.com")
                .password("password123")
                .address("Via Roma 123")
                .phoneNumber("3331234567")
                .zipCode(20100)
                .taxIdCode("RSSMRA90E15H501A")
                .birthDate(LocalDate.of(1990, 5, 15))
                .birthPlace("Milano")
                .authUserId("auth-123")
                .build();

        // When
        String toString = user.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("uid-123"));
        assertTrue(toString.contains("Mario"));
        assertTrue(toString.contains("Rossi"));
        assertTrue(toString.contains("mario.rossi@example.com"));
    }

    @Test
    void testUserAllArgsConstructor() {
        // Given
        LocalDate birthDate = LocalDate.of(1990, 5, 15);

        // When
        User user = new User(
                "uid-123",
                "Mario",
                "Giovanni",
                "Rossi",
                "mario.rossi@example.com",
                "password123",
                "Via Roma 123",
                "3331234567",
                SubscriptionType.FREE,
                20100,
                "RSSMRA90E15H501A",
                birthDate,
                "Milano",
                "Milano",
                "MI",
                null,
                "auth-123"
        );

        // Then
        assertNotNull(user);
        assertEquals("uid-123", user.getUid());
        assertEquals("Mario", user.getName());
        assertEquals("Rossi", user.getLastName());
    }

    @Test
    void testUserNoArgsConstructor() {
        // When
        User user = new User();

        // Then
        assertNotNull(user);
        assertNull(user.getUid());
        assertNull(user.getName());
    }

    @Test
    void testUserWithNullSecondName() {
        // Given & When
        User user = User.builder()
                .uid("uid-123")
                .name("Mario")
                .secondName(null)
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

        // Then
        assertNull(user.getSecondName());
    }
}

