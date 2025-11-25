package com.giuliopastore.BankApp.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransferStatusTest {

    @Test
    void testTransferStatusValues() {
        // When
        TransferStatus[] statuses = TransferStatus.values();

        // Then
        assertEquals(4, statuses.length);
        assertEquals(TransferStatus.SENT, statuses[0]);
        assertEquals(TransferStatus.RECEIVED, statuses[1]);
        assertEquals(TransferStatus.REJECTED, statuses[2]);
        assertEquals(TransferStatus.PENDING, statuses[3]);
    }

    @Test
    void testTransferStatusSent() {
        // Given
        TransferStatus status = TransferStatus.SENT;

        // Then
        assertEquals("SENT", status.getValue());
        assertEquals("SENT", status.name());
    }

    @Test
    void testTransferStatusReceived() {
        // Given
        TransferStatus status = TransferStatus.RECEIVED;

        // Then
        assertEquals("RECEIVED", status.getValue());
        assertEquals("RECEIVED", status.name());
    }

    @Test
    void testTransferStatusRejected() {
        // Given
        TransferStatus status = TransferStatus.REJECTED;

        // Then
        assertEquals("REJECTED", status.getValue());
        assertEquals("REJECTED", status.name());
    }

    @Test
    void testTransferStatusPending() {
        // Given
        TransferStatus status = TransferStatus.PENDING;

        // Then
        assertEquals("PENDING", status.getValue());
        assertEquals("PENDING", status.name());
    }

    @Test
    void testTransferStatusValueOf() {
        // When & Then
        assertEquals(TransferStatus.SENT, TransferStatus.valueOf("SENT"));
        assertEquals(TransferStatus.RECEIVED, TransferStatus.valueOf("RECEIVED"));
        assertEquals(TransferStatus.REJECTED, TransferStatus.valueOf("REJECTED"));
        assertEquals(TransferStatus.PENDING, TransferStatus.valueOf("PENDING"));
    }

    @Test
    void testTransferStatusValueOfInvalid() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> TransferStatus.valueOf("INVALID"));
    }

    @Test
    void testTransferStatusEquality() {
        // Given
        TransferStatus status1 = TransferStatus.SENT;
        TransferStatus status2 = TransferStatus.SENT;
        TransferStatus status3 = TransferStatus.PENDING;

        // Then
        assertEquals(status1, status2);
        assertNotEquals(status1, status3);
    }

    @Test
    void testTransferStatusGetValue() {
        // When & Then
        assertEquals("SENT", TransferStatus.SENT.getValue());
        assertEquals("RECEIVED", TransferStatus.RECEIVED.getValue());
        assertEquals("REJECTED", TransferStatus.REJECTED.getValue());
        assertEquals("PENDING", TransferStatus.PENDING.getValue());
    }

    @Test
    void testAllTransferStatusesHaveValues() {
        // Given & When
        for (TransferStatus status : TransferStatus.values()) {
            // Then
            assertNotNull(status.getValue());
            assertFalse(status.getValue().isEmpty());
        }
    }

    @Test
    void testTransferStatusWorkflow() {
        // Test typical transfer workflow: PENDING -> SENT -> RECEIVED
        TransferStatus pending = TransferStatus.PENDING;
        TransferStatus sent = TransferStatus.SENT;
        TransferStatus received = TransferStatus.RECEIVED;

        assertNotEquals(pending, sent);
        assertNotEquals(sent, received);
        assertNotEquals(pending, received);
    }
}

