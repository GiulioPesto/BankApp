package com.giuliopastore.BankApp.services;

import com.giuliopastore.BankApp.entities.user.User;
import com.giuliopastore.BankApp.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        // Arrange
        User user = User.builder()
                .name("Giulio")
                .lastName("Pastore")
                .email("giulio@gmail.com")
                .password("12345")
                .address("Via bla, 32")
                .phoneNumber("123456789")
                .zipCode(73100)
                .taxIdCode("PSTGFD45FE535")
                .age(20)
                .build();
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User createdUser = userService.createUser(user);

        // Assert
        assertNotNull(createdUser);
        assertEquals("Giulio", createdUser.getName());
        verify(userRepository, times(1)).save(user);
    }
}

