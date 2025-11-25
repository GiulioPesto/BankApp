package com.giuliopastore.BankApp.entities.transfer;

import com.giuliopastore.BankApp.enums.TransferStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransferTest {

    @Test
    void testTransferCreationWithBuilder() {
        // Given
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(2);

        // When
        Transfer transfer = Transfer.builder()
                .uid("transfer-uid-123")
                .fromAccountUid("account-from-123")
                .toAccountUid("account-to-456")
                .amount(new BigDecimal("500.00"))
                .fees(new BigDecimal("2.50"))
                .reason("Pagamento fattura")
                .status(TransferStatus.PENDING)
                .transferStartDateTime(startTime)
                .transferEndDateTime(endTime)
                .build();

        // Then
        assertNotNull(transfer);
        assertEquals("transfer-uid-123", transfer.getUid());
        assertEquals("account-from-123", transfer.getFromAccountUid());
        assertEquals("account-to-456", transfer.getToAccountUid());
        assertEquals(new BigDecimal("500.00"), transfer.getAmount());
        assertEquals(new BigDecimal("2.50"), transfer.getFees());
        assertEquals("Pagamento fattura", transfer.getReason());
        assertEquals(TransferStatus.PENDING, transfer.getStatus());
        assertEquals(startTime, transfer.getTransferStartDateTime());
        assertEquals(endTime, transfer.getTransferEndDateTime());
    }

    @Test
    void testTransferAllArgsConstructor() {
        // Given
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);

        // When
        Transfer transfer = new Transfer(
                "transfer-uid-456",
                "account-from-789",
                "account-to-012",
                new BigDecimal("1000.00"),
                new BigDecimal("5.00"),
                "Bonifico mensile",
                TransferStatus.SENT,
                startTime,
                endTime
        );

        // Then
        assertNotNull(transfer);
        assertEquals("transfer-uid-456", transfer.getUid());
        assertEquals("account-from-789", transfer.getFromAccountUid());
        assertEquals("account-to-012", transfer.getToAccountUid());
        assertEquals(new BigDecimal("1000.00"), transfer.getAmount());
        assertEquals(new BigDecimal("5.00"), transfer.getFees());
        assertEquals("Bonifico mensile", transfer.getReason());
        assertEquals(TransferStatus.SENT, transfer.getStatus());
    }

    @Test
    void testTransferNoArgsConstructor() {
        // When
        Transfer transfer = new Transfer();

        // Then
        assertNotNull(transfer);
        assertNull(transfer.getUid());
        assertNull(transfer.getFromAccountUid());
        assertNull(transfer.getToAccountUid());
        assertNull(transfer.getAmount());
        assertNull(transfer.getFees());
        assertNull(transfer.getReason());
        assertNull(transfer.getStatus());
    }

    @Test
    void testTransferGettersAndSetters() {
        // Given
        Transfer transfer = new Transfer();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(3);

        // When
        transfer.setUid("transfer-789");
        transfer.setFromAccountUid("from-123");
        transfer.setToAccountUid("to-456");
        transfer.setAmount(new BigDecimal("750.00"));
        transfer.setFees(new BigDecimal("3.75"));
        transfer.setReason("Rimborso");
        transfer.setStatus(TransferStatus.RECEIVED);
        transfer.setTransferStartDateTime(startTime);
        transfer.setTransferEndDateTime(endTime);

        // Then
        assertEquals("transfer-789", transfer.getUid());
        assertEquals("from-123", transfer.getFromAccountUid());
        assertEquals("to-456", transfer.getToAccountUid());
        assertEquals(new BigDecimal("750.00"), transfer.getAmount());
        assertEquals(new BigDecimal("3.75"), transfer.getFees());
        assertEquals("Rimborso", transfer.getReason());
        assertEquals(TransferStatus.RECEIVED, transfer.getStatus());
        assertEquals(startTime, transfer.getTransferStartDateTime());
        assertEquals(endTime, transfer.getTransferEndDateTime());
    }

    @Test
    void testTransferToString() {
        // Given
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);
        Transfer transfer = Transfer.builder()
                .uid("transfer-uid-123")
                .fromAccountUid("account-from-123")
                .toAccountUid("account-to-456")
                .amount(new BigDecimal("500.00"))
                .fees(new BigDecimal("2.50"))
                .reason("Test transfer")
                .status(TransferStatus.SENT)
                .transferStartDateTime(startTime)
                .transferEndDateTime(endTime)
                .build();

        // When
        String toString = transfer.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("transfer-uid-123"));
        assertTrue(toString.contains("account-from-123"));
        assertTrue(toString.contains("account-to-456"));
    }

    @Test
    void testTransferWithAllStatuses() {
        // Test PENDING
        Transfer pendingTransfer = createTransfer(TransferStatus.PENDING);
        assertEquals(TransferStatus.PENDING, pendingTransfer.getStatus());

        // Test SENT
        Transfer sentTransfer = createTransfer(TransferStatus.SENT);
        assertEquals(TransferStatus.SENT, sentTransfer.getStatus());

        // Test RECEIVED
        Transfer receivedTransfer = createTransfer(TransferStatus.RECEIVED);
        assertEquals(TransferStatus.RECEIVED, receivedTransfer.getStatus());

        // Test REJECTED
        Transfer rejectedTransfer = createTransfer(TransferStatus.REJECTED);
        assertEquals(TransferStatus.REJECTED, rejectedTransfer.getStatus());
    }

    @Test
    void testTransferWithNullReason() {
        // Given & When
        Transfer transfer = Transfer.builder()
                .uid("transfer-uid-123")
                .fromAccountUid("account-from-123")
                .toAccountUid("account-to-456")
                .amount(new BigDecimal("100.00"))
                .fees(new BigDecimal("1.00"))
                .reason(null)
                .status(TransferStatus.SENT)
                .transferStartDateTime(LocalDateTime.now())
                .transferEndDateTime(LocalDateTime.now().plusHours(1))
                .build();

        // Then
        assertNull(transfer.getReason());
    }

    @Test
    void testTransferWithSmallAmount() {
        // Given & When
        Transfer transfer = Transfer.builder()
                .uid("transfer-uid-123")
                .fromAccountUid("account-from-123")
                .toAccountUid("account-to-456")
                .amount(new BigDecimal("0.01"))
                .fees(new BigDecimal("0.00"))
                .status(TransferStatus.SENT)
                .transferStartDateTime(LocalDateTime.now())
                .transferEndDateTime(LocalDateTime.now().plusHours(1))
                .build();

        // Then
        assertEquals(new BigDecimal("0.01"), transfer.getAmount());
    }

    @Test
    void testTransferWithLargeAmount() {
        // Given & When
        Transfer transfer = Transfer.builder()
                .uid("transfer-uid-123")
                .fromAccountUid("account-from-123")
                .toAccountUid("account-to-456")
                .amount(new BigDecimal("1000000.00"))
                .fees(new BigDecimal("100.00"))
                .status(TransferStatus.SENT)
                .transferStartDateTime(LocalDateTime.now())
                .transferEndDateTime(LocalDateTime.now().plusHours(1))
                .build();

        // Then
        assertEquals(new BigDecimal("1000000.00"), transfer.getAmount());
    }

    private Transfer createTransfer(TransferStatus status) {
        return Transfer.builder()
                .uid("transfer-uid-" + status.name())
                .fromAccountUid("account-from")
                .toAccountUid("account-to")
                .amount(new BigDecimal("100.00"))
                .fees(new BigDecimal("1.00"))
                .status(status)
                .transferStartDateTime(LocalDateTime.now())
                .transferEndDateTime(LocalDateTime.now().plusHours(1))
                .build();
    }
}

