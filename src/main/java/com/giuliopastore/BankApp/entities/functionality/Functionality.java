package com.giuliopastore.BankApp.entities.functionality;

import com.giuliopastore.BankApp.entities.listener.GenerateUidEntityListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(schema = "bank_schema", name = "functionality")
@EntityListeners(GenerateUidEntityListener.class)
public class Functionality {

    @Id
    @Column(length = 36, columnDefinition = "VARCHAR(36)")
    @NotNull
    private String uid;

    @NotNull
    private String name;

    @NotNull
    private String description;

}
