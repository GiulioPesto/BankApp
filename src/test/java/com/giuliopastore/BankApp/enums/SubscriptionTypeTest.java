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
        assertEquals(SubscriptionType.STANDARD, types[1]);
        assertEquals(SubscriptionType.PREMIUM, types[2]);
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
        SubscriptionType type = SubscriptionType.STANDARD;

        // Then
        assertEquals("STANDARD", type.getValue());
        assertEquals("STANDARD", type.name());
    }

    @Test
    void testSubscriptionTypePremium() {
        // Given
        SubscriptionType type = SubscriptionType.PREMIUM;

        // Then
        assertEquals("PREMIUM", type.getValue());
        assertEquals("PREMIUM", type.name());
    }

    @Test
    void testSubscriptionTypeValueOf() {
        // When & Then
        assertEquals(SubscriptionType.FREE, SubscriptionType.valueOf("FREE"));
        assertEquals(SubscriptionType.STANDARD, SubscriptionType.valueOf("STANDARD"));
        assertEquals(SubscriptionType.PREMIUM, SubscriptionType.valueOf("PREMIUM"));
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
        SubscriptionType type3 = SubscriptionType.PREMIUM;

        // Then
        assertEquals(type1, type2);
        assertNotEquals(type1, type3);
    }

    @Test
    void testSubscriptionTypeGetValue() {
        // When & Then
        assertEquals("FREE", SubscriptionType.FREE.getValue());
        assertEquals("STANDARD", SubscriptionType.STANDARD.getValue());
        assertEquals("PREMIUM", SubscriptionType.PREMIUM.getValue());
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

