package com.giuliopastore.BankApp.entities.listener;

import jakarta.persistence.PrePersist;
import java.lang.reflect.Field;

public class GenerateUidEntityListener {
    @PrePersist
    public void generateUid(Object entity) {
        try {
            Field uidField = entity.getClass().getDeclaredField("uid");
            uidField.setAccessible(true);
            Object value = uidField.get(entity);
            if (value == null) {
                uidField.set(entity, java.util.UUID.randomUUID().toString());
            }
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
            // L'entità non ha campo uid o non è accessibile: ignora
        }
    }
}