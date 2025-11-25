package com.giuliopastore.BankApp.entities.listener;

import com.giuliopastore.BankApp.entities.user.User;
import com.giuliopastore.BankApp.entities.bankAccount.BankAccount;
import com.giuliopastore.BankApp.entities.transfer.Transfer;
import com.giuliopastore.BankApp.entities.role.Role;
import com.giuliopastore.BankApp.entities.functionality.Functionality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenerateUidEntityListenerTest {

    private GenerateUidEntityListener listener;

    @BeforeEach
    void setUp() {
        listener = new GenerateUidEntityListener();
    }

    @Test
    void testGenerateUidForUserWithNullUid() {
        // Given
        User user = new User();
        assertNull(user.getUid());

        // When
        listener.generateUid(user);

        // Then
        assertNotNull(user.getUid());
        assertTrue(user.getUid().matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"));
    }

    @Test
    void testGenerateUidForUserWithExistingUid() {
        // Given
        User user = new User();
        String existingUid = "existing-uid-123";
        user.setUid(existingUid);

        // When
        listener.generateUid(user);

        // Then
        assertEquals(existingUid, user.getUid());
    }

    @Test
    void testGenerateUidForBankAccountWithNullUid() {
        // Given
        BankAccount account = new BankAccount();
        assertNull(account.getUid());

        // When
        listener.generateUid(account);

        // Then
        assertNotNull(account.getUid());
        assertTrue(account.getUid().matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"));
    }

    @Test
    void testGenerateUidForBankAccountWithExistingUid() {
        // Given
        BankAccount account = new BankAccount();
        String existingUid = "existing-account-uid";
        account.setUid(existingUid);

        // When
        listener.generateUid(account);

        // Then
        assertEquals(existingUid, account.getUid());
    }

    @Test
    void testGenerateUidForTransferWithNullUid() {
        // Given
        Transfer transfer = new Transfer();
        assertNull(transfer.getUid());

        // When
        listener.generateUid(transfer);

        // Then
        assertNotNull(transfer.getUid());
        assertTrue(transfer.getUid().matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"));
    }

    @Test
    void testGenerateUidForRoleWithNullUid() {
        // Given
        Role role = new Role();
        assertNull(role.getUid());

        // When
        listener.generateUid(role);

        // Then
        assertNotNull(role.getUid());
        assertTrue(role.getUid().matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"));
    }

    @Test
    void testGenerateUidForFunctionalityWithNullUid() {
        // Given
        Functionality functionality = new Functionality();
        assertNull(functionality.getUid());

        // When
        listener.generateUid(functionality);

        // Then
        assertNotNull(functionality.getUid());
        assertTrue(functionality.getUid().matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"));
    }

    @Test
    void testGenerateUidCreatesUniqueIds() {
        // Given
        User user1 = new User();
        User user2 = new User();

        // When
        listener.generateUid(user1);
        listener.generateUid(user2);

        // Then
        assertNotNull(user1.getUid());
        assertNotNull(user2.getUid());
        assertNotEquals(user1.getUid(), user2.getUid());
    }

    @Test
    void testGenerateUidWithObjectWithoutUidField() {
        // Given
        Object objectWithoutUid = new Object();

        // When & Then - Should not throw exception
        assertDoesNotThrow(() -> listener.generateUid(objectWithoutUid));
    }

    @Test
    void testGenerateUidMultipleTimes() {
        // Given
        User user = new User();

        // When
        listener.generateUid(user);
        String firstUid = user.getUid();
        listener.generateUid(user);
        String secondUid = user.getUid();

        // Then - UID should not change after first generation
        assertEquals(firstUid, secondUid);
    }
}

