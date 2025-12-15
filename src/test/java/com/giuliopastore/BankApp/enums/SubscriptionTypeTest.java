package com.giuliopastore.BankApp.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionTypeTest {

    @Test
    void testSubscriptionTypeValues() {
        // When
        SubscriptionType[] types = SubscriptionType.values();

        // Then
        assertEquals(3, types.length);
        assertEquals(SubscriptionType.FREE, types[0]);
        assertEquals(SubscriptionType.SILVER, types[1]);
        assertEquals(SubscriptionType.GOLD, types[2]);
    }

    @Test
    void testSubscriptionTypeFree() {
        // Given
        SubscriptionType type = SubscriptionType.FREE;

        // Then
        assertEquals("FREE", type.getValue());
        assertEquals("FREE", type.name());
    }

    @Test
    void testSubscriptionTypeStandard() {
        // Given
        SubscriptionType type = SubscriptionType.SILVER;

        // Then
        assertEquals("SILVER", type.getValue());
        assertEquals("SILVER", type.name());
    }

    @Test
    void testSubscriptionTypePremium() {
        // Given
        SubscriptionType type = SubscriptionType.GOLD;

        // Then
        assertEquals("GOLD", type.getValue());
        assertEquals("GOLD", type.name());
    }

    @Test
    void testSubscriptionTypeValueOf() {
        // When & Then
        assertEquals(SubscriptionType.FREE, SubscriptionType.valueOf("FREE"));
        assertEquals(SubscriptionType.SILVER, SubscriptionType.valueOf("SILVER"));
        assertEquals(SubscriptionType.GOLD, SubscriptionType.valueOf("GOLD"));
    }

    @Test
    void testSubscriptionTypeValueOfInvalid() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> SubscriptionType.valueOf("INVALID"));
    }

    @Test
    void testSubscriptionTypeEquality() {
        // Given
        SubscriptionType type1 = SubscriptionType.FREE;
        SubscriptionType type2 = SubscriptionType.FREE;
        SubscriptionType type3 = SubscriptionType.GOLD;

        // Then
        assertEquals(type1, type2);
        assertNotEquals(type1, type3);
    }

    @Test
    void testSubscriptionTypeGetValue() {
        // When & Then
        assertEquals("FREE", SubscriptionType.FREE.getValue());
        assertEquals("SILVER", SubscriptionType.SILVER.getValue());
        assertEquals("GOLD", SubscriptionType.GOLD.getValue());
    }

    @Test
    void testAllSubscriptionTypesHaveValues() {
        // Given & When
        for (SubscriptionType type : SubscriptionType.values()) {
            // Then
            assertNotNull(type.getValue());
            assertFalse(type.getValue().isEmpty());
        }
    }
}

