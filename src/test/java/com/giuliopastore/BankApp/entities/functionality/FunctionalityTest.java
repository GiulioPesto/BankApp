package com.giuliopastore.BankApp.entities.functionality;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalityTest {

    @Test
    void testFunctionalityCreation() {
        // Given & When
        Functionality functionality = new Functionality();
        functionality.setUid("func-uid-123");
        functionality.setName("TRANSFER");
        functionality.setDescription("Ability to make bank transfers");

        // Then
        assertNotNull(functionality);
        assertEquals("func-uid-123", functionality.getUid());
        assertEquals("TRANSFER", functionality.getName());
        assertEquals("Ability to make bank transfers", functionality.getDescription());
    }

    @Test
    void testFunctionalityAllArgsConstructor() {
        // Given & When
        Functionality functionality = new Functionality(
                "func-uid-456",
                "VIEW_BALANCE",
                "Ability to view account balance"
        );

        // Then
        assertNotNull(functionality);
        assertEquals("func-uid-456", functionality.getUid());
        assertEquals("VIEW_BALANCE", functionality.getName());
        assertEquals("Ability to view account balance", functionality.getDescription());
    }

    @Test
    void testFunctionalityNoArgsConstructor() {
        // When
        Functionality functionality = new Functionality();

        // Then
        assertNotNull(functionality);
        assertNull(functionality.getUid());
        assertNull(functionality.getName());
        assertNull(functionality.getDescription());
    }

    @Test
    void testFunctionalityGettersAndSetters() {
        // Given
        Functionality functionality = new Functionality();

        // When
        functionality.setUid("func-789");
        functionality.setName("DEPOSIT");
        functionality.setDescription("Ability to deposit money");

        // Then
        assertEquals("func-789", functionality.getUid());
        assertEquals("DEPOSIT", functionality.getName());
        assertEquals("Ability to deposit money", functionality.getDescription());
    }

    @Test
    void testMultipleFunctionalityTypes() {
        // Given & When
        Functionality transferFunc = new Functionality("func-1", "TRANSFER", "Transfer money");
        Functionality viewFunc = new Functionality("func-2", "VIEW", "View accounts");
        Functionality depositFunc = new Functionality("func-3", "DEPOSIT", "Deposit money");

        // Then
        assertNotEquals(transferFunc.getUid(), viewFunc.getUid());
        assertNotEquals(transferFunc.getName(), viewFunc.getName());
        assertNotEquals(viewFunc.getUid(), depositFunc.getUid());
    }

    @Test
    void testFunctionalityWithEmptyDescription() {
        // Given & When
        Functionality functionality = new Functionality("func-uid", "WITHDRAW", "");

        // Then
        assertEquals("", functionality.getDescription());
    }

    @Test
    void testFunctionalityWithLongDescription() {
        // Given
        String longDescription = "This functionality allows users to perform complex operations " +
                "including multi-currency transfers, scheduled payments, and recurring " +
                "transactions with advanced security features and audit logging.";

        // When
        Functionality functionality = new Functionality("func-uid", "ADVANCED_TRANSFER", longDescription);

        // Then
        assertEquals(longDescription, functionality.getDescription());
        assertTrue(functionality.getDescription().length() > 100);
    }

    @Test
    void testCommonBankFunctionalities() {
        // Given & When
        Functionality[] functionalities = {
                new Functionality("func-1", "VIEW_BALANCE", "View account balance"),
                new Functionality("func-2", "TRANSFER", "Make transfers"),
                new Functionality("func-3", "DEPOSIT", "Deposit money"),
                new Functionality("func-4", "WITHDRAW", "Withdraw money"),
                new Functionality("func-5", "VIEW_HISTORY", "View transaction history")
        };

        // Then
        assertEquals(5, functionalities.length);
        for (Functionality func : functionalities) {
            assertNotNull(func.getUid());
            assertNotNull(func.getName());
            assertNotNull(func.getDescription());
        }
    }
}

