package com.giuliopastore.BankApp.entities.transfer;

import com.giuliopastore.BankApp.entities.listener.GenerateUidEntityListener;
import com.giuliopastore.BankApp.enums.TransferStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table( name = "transfer")
@EntityListeners(GenerateUidEntityListener.class)
@ToString
public class Transfer {

    @Id
    @Column(length = 36, columnDefinition = "VARCHAR(36)")
    @NotNull
    private String uid;

    @NotNull
    private String fromAccountUid;

    @NotNull
    private String toAccountUid;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    @Positive
    private BigDecimal fees;

    private String reason;

    private TransferStatus status;

    @NotNull
    @FutureOrPresent
    private LocalDateTime transferStartDateTime;

    @NotNull
    @FutureOrPresent
    private LocalDateTime transferEndDateTime;
}
