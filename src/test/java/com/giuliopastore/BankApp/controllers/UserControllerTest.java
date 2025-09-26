package com.giuliopastore.BankApp.controllers;


import com.giuliopastore.BankApp.BankAppApplication;
import com.giuliopastore.BankApp.entities.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
                .birthDate(LocalDate.parse("2020/10/10"))
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
        Assertions.assertEquals(user.getUid(), result.getUid());
        log.info("User retrieved successfully: {}", result);
    }

    @Test
    public void testUpdateUser() {
        User newUser = user;
        newUser.setName("Updated Name");
        User updatedUser = controller.updateUser(user.getUid(), newUser);

        assertNotNull(updatedUser);
        Assertions.assertEquals("Updated Name", updatedUser.getName());
        log.info("User updated successfully: {}", updatedUser);
    }

    @Test
    public void testDeleteUser() {
        controller.deleteUser(user.getUid());
        User deletedUser = controller.getUserByUid(user.getUid());
        Assertions.assertNull(deletedUser);
        log.info("User deleted successfully: {}", user.getUid());
    }
}
