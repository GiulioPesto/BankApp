package com.giuliopastore.BankApp.services;

import com.giuliopastore.BankApp.entities.user.User;
import com.giuliopastore.BankApp.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .uid("test-uid-123")
                .name("Mario")
                .lastName("Rossi")
                .email("mario.rossi@example.com")
                .password("password123")
                .address("Via Roma 123")
                .phoneNumber("3331234567")
                .zipCode(20100)
                .taxIdCode("RSSMRA90E15H501A")
                .birthDate(LocalDate.of(1990, 5, 15))
                .birthPlace("Milano")
                .city("Milano")
                .province("MI")
                .authUserId("auth-123")
                .build();
    }

    @Test
    void testCreateUser() {
        // Given
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        User result = userService.createUser(testUser);

        // Then
        assertNotNull(result);
        assertEquals("test-uid-123", result.getUid());
        assertEquals("Mario", result.getName());
        assertEquals("Rossi", result.getLastName());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void testGetUserByUid() {
        // Given
        String uid = "test-uid-123";
        when(userRepository.findUserByUid(uid)).thenReturn(testUser);

        // When
        User result = userService.getUserByUid(uid);

        // Then
        assertNotNull(result);
        assertEquals(uid, result.getUid());
        assertEquals("Mario", result.getName());
        verify(userRepository, times(1)).findUserByUid(uid);
    }

    @Test
    void testGetUserByUidNotFound() {
        // Given
        String uid = "non-existent-uid";
        when(userRepository.findUserByUid(uid)).thenReturn(null);

        // When
        User result = userService.getUserByUid(uid);

        // Then
        assertNull(result);
        verify(userRepository, times(1)).findUserByUid(uid);
    }

    @Test
    void testUpdateUser() {
        // Given
        String uid = "test-uid-123";
        User updatedUser = User.builder()
                .name("Luigi")
                .lastName("Bianchi")
                .email("luigi.bianchi@example.com")
                .password("newpassword")
                .address("Via Verdi 456")
                .phoneNumber("3339876543")
                .zipCode(10100)
                .taxIdCode("BNCLGI85M10L219X")
                .birthDate(LocalDate.of(1985, 8, 10))
                .birthPlace("Torino")
                .city("Torino")
                .province("TO")
                .build();

        when(userRepository.findUserByUid(uid)).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        User result = userService.updateUser(uid, updatedUser);

        // Then
        assertNotNull(result);
        assertEquals("Luigi", result.getName());
        assertEquals("Bianchi", result.getLastName());
        assertEquals("luigi.bianchi@example.com", result.getEmail());
        verify(userRepository, times(1)).findUserByUid(uid);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUserNotFound() {
        // Given
        String uid = "non-existent-uid";
        User updatedUser = User.builder()
                .name("Luigi")
                .lastName("Bianchi")
                .build();

        when(userRepository.findUserByUid(uid)).thenReturn(null);

        // When
        User result = userService.updateUser(uid, updatedUser);

        // Then
        assertNull(result);
        verify(userRepository, times(1)).findUserByUid(uid);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testUpdateUserWithException() {
        // Given
        String uid = "test-uid-123";
        User updatedUser = User.builder()
                .name("Luigi")
                .lastName("Bianchi")
                .email("luigi.bianchi@example.com")
                .password("newpassword")
                .address("Via Verdi 456")
                .phoneNumber("3339876543")
                .zipCode(10100)
                .taxIdCode("BNCLGI85M10L219X")
                .birthDate(LocalDate.of(1985, 8, 10))
                .birthPlace("Torino")
                .city("Torino")
                .province("TO")
                .build();

        when(userRepository.findUserByUid(uid)).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Database error"));

        // When & Then
        assertThrows(NullPointerException.class, () -> userService.updateUser(uid, updatedUser));

        verify(userRepository, times(1)).findUserByUid(uid);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        // Given
        String uid = "test-uid-123";
        when(userRepository.findUserByUid(uid)).thenReturn(testUser);
        doNothing().when(userRepository).delete(testUser);

        // When
        userService.deleteUser(uid);

        // Then
        verify(userRepository, times(1)).findUserByUid(uid);
        verify(userRepository, times(1)).delete(testUser);
    }

    @Test
    void testDeleteUserNotFound() {
        // Given
        String uid = "non-existent-uid";
        when(userRepository.findUserByUid(uid)).thenReturn(null);

        // When
        userService.deleteUser(uid);

        // Then
        verify(userRepository, times(1)).findUserByUid(uid);
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void testUpdateUserPartialFields() {
        // Given
        String uid = "test-uid-123";
        User partialUpdate = User.builder()
                .name("UpdatedName")
                .lastName("UpdatedLastName")
                .email(testUser.getEmail())
                .password(testUser.getPassword())
                .address(testUser.getAddress())
                .phoneNumber(testUser.getPhoneNumber())
                .zipCode(testUser.getZipCode())
                .taxIdCode(testUser.getTaxIdCode())
                .birthDate(testUser.getBirthDate())
                .birthPlace(testUser.getBirthPlace())
                .city(testUser.getCity())
                .province(testUser.getProvince())
                .build();

        when(userRepository.findUserByUid(uid)).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        User result = userService.updateUser(uid, partialUpdate);

        // Then
        assertNotNull(result);
        assertEquals("UpdatedName", result.getName());
        assertEquals("UpdatedLastName", result.getLastName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUserNullUser() {
        // Given
        when(userRepository.save(any())).thenThrow(new IllegalArgumentException("User cannot be null"));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(null));
    }

    @Test
    void testGetUserByUidEmptyString() {
        // Given
        String uid = "";
        when(userRepository.findUserByUid(uid)).thenReturn(null);

        // When
        User result = userService.getUserByUid(uid);

        // Then
        assertNull(result);
        verify(userRepository, times(1)).findUserByUid(uid);
    }
}

