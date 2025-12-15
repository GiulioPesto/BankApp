package com.giuliopastore.BankApp.controllers;


import com.giuliopastore.BankApp.BankAppApplication;
import com.giuliopastore.BankApp.entities.user.User;
import com.giuliopastore.BankApp.enums.SubscriptionType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(classes = {BankAppApplication.class})
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private UserController controller;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .name("Giulio")
                .lastName("Pastore")
                .email("giulio@gmail.com")
                .password("12345")
                .address("Via bla, 32")
                .phoneNumber("123456789")
                .zipCode(73100)
                .taxIdCode("PSTGFD45FE535")
                .birthDate(LocalDate.parse("2020-10-10"))
                .subscriptionType(null)
                .birthPlace("Lecce")
                .city("Lecce")
                .province("LE")
                .build();

        user = controller.createUser(user);

        assertNotNull(user);

        log.info("User created successfully: {}", user);
    }

    @Test
    public void testGetUser() {

        User result = controller.getUserByUid(user.getUid());

        assertNotNull(result);
        assertEquals(user.getUid(), result.getUid());
        log.info("User retrieved successfully: {}", result);
    }

    @Test
    public void testUpdateUser() {
        User newUser = user;
        newUser.setName("Updated Name");
        User updatedUser = controller.updateUser(user.getUid(), newUser);

        assertNotNull(updatedUser);
        assertEquals("Updated Name", updatedUser.getName());
        log.info("User updated successfully: {}", updatedUser);
    }

    @Test
    public void testDeleteUser() {
        controller.deleteUser(user.getUid());
        User deletedUser = controller.getUserByUid(user.getUid());
        assertNull(deletedUser);
        log.info("User deleted successfully: {}", user.getUid());
    }

    @Test
    public void testCreateUserWithAllFields() {
        User newUser = User.builder()
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
                .subscriptionType(SubscriptionType.GOLD)
                .birthPlace("Milano")
                .city("Milano")
                .province("MI")
                .authUserId("auth-mario-123")
                .build();

        User created = controller.createUser(newUser);

        assertNotNull(created);
        assertNotNull(created.getUid());
        assertEquals("Mario", created.getName());
        assertEquals("Giovanni", created.getSecondName());
        assertEquals("Rossi", created.getLastName());
        assertEquals(SubscriptionType.GOLD, created.getSubscriptionType());
        log.info("User with all fields created successfully: {}", created);

        // Cleanup
        controller.deleteUser(created.getUid());
    }

    @Test
    public void testUpdateUserMultipleFields() {
        User updatedData = User.builder()
                .name("UpdatedName")
                .lastName("UpdatedLastName")
                .email("updated@email.com")
                .password("newpassword")
                .address("New Address 456")
                .phoneNumber("9998887776")
                .zipCode(10100)
                .taxIdCode("NEWCODE123")
                .birthDate(LocalDate.of(1985, 3, 20))
                .birthPlace("Torino")
                .city("Torino")
                .province("TO")
                .authUserId(user.getAuthUserId())
                .build();

        User updated = controller.updateUser(user.getUid(), updatedData);

        assertNotNull(updated);
        assertEquals("UpdatedName", updated.getName());
        assertEquals("UpdatedLastName", updated.getLastName());
        assertEquals("updated@email.com", updated.getEmail());
        assertEquals("New Address 456", updated.getAddress());
        log.info("User with multiple fields updated successfully: {}", updated);
    }

    @Test
    public void testGetNonExistentUser() {
        User result = controller.getUserByUid("non-existent-uid-12345");

        assertNull(result);
        log.info("Non-existent user test passed");
    }

    @Test
    public void testDeleteNonExistentUser() {
        // Should not throw exception
        assertDoesNotThrow(() -> controller.deleteUser("non-existent-uid-12345"));
        log.info("Delete non-existent user test passed");
    }

    @Test
    public void testUpdateNonExistentUser() {
        User updateData = User.builder()
                .name("Test")
                .lastName("User")
                .email("test@example.com")
                .password("password")
                .address("Address")
                .phoneNumber("1234567890")
                .zipCode(12345)
                .taxIdCode("TESTCODE")
                .birthDate(LocalDate.now())
                .birthPlace("Place")
                .city("City")
                .province("PR")
                .authUserId("auth-123")
                .build();

        User result = controller.updateUser("non-existent-uid-12345", updateData);

        assertNull(result);
        log.info("Update non-existent user test passed");
    }
}
