package com.giuliopastore.BankApp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giuliopastore.BankApp.entities.user.User;
import com.giuliopastore.BankApp.enums.SubscriptionType;
import com.giuliopastore.BankApp.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
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
                .subscriptionType(SubscriptionType.GOLD)
                .authUserId("auth-123")
                .build();
    }

    @Test
    void testCreateUser() throws Exception {
        // Given
        when(userService.createUser(any(User.class))).thenReturn(testUser);

        // When & Then
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uid").value("test-uid-123"))
                .andExpect(jsonPath("$.name").value("Mario"))
                .andExpect(jsonPath("$.lastName").value("Rossi"))
                .andExpect(jsonPath("$.email").value("mario.rossi@example.com"));

        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    void testGetUserByUid() throws Exception {
        // Given
        String uid = "test-uid-123";
        when(userService.getUserByUid(uid)).thenReturn(testUser);

        // When & Then
        mockMvc.perform(get("/user/{uid}", uid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uid").value("test-uid-123"))
                .andExpect(jsonPath("$.name").value("Mario"))
                .andExpect(jsonPath("$.lastName").value("Rossi"));

        verify(userService, times(1)).getUserByUid(uid);
    }

    @Test
    void testGetUserByUidNotFound() throws Exception {
        // Given
        String uid = "non-existent-uid";
        when(userService.getUserByUid(uid)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/user/{uid}", uid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        verify(userService, times(1)).getUserByUid(uid);
    }

    @Test
    void testUpdateUser() throws Exception {
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
                .authUserId("auth-456")
                .build();

        User updatedResult = User.builder()
                .uid(uid)
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
                .authUserId("auth-456")
                .build();

        when(userService.updateUser(eq(uid), any(User.class))).thenReturn(updatedResult);

        // When & Then
        mockMvc.perform(patch("/user/{uid}", uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uid").value(uid))
                .andExpect(jsonPath("$.name").value("Luigi"))
                .andExpect(jsonPath("$.lastName").value("Bianchi"));

        verify(userService, times(1)).updateUser(eq(uid), any(User.class));
    }

    @Test
    void testUpdateUserNotFound() throws Exception {
        // Given
        String uid = "non-existent-uid";
        User updateData = User.builder()
                .name("Test")
                .lastName("User")
                .build();

        when(userService.updateUser(eq(uid), any(User.class))).thenReturn(null);

        // When & Then
        mockMvc.perform(patch("/user/{uid}", uid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        verify(userService, times(1)).updateUser(eq(uid), any(User.class));
    }

    @Test
    void testDeleteUser() throws Exception {
        // Given
        String uid = "test-uid-123";
        doNothing().when(userService).deleteUser(uid);

        // When & Then
        mockMvc.perform(delete("/user/{uid}", uid))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(uid);
    }

    @Test
    void testCreateUserWithMinimalFields() throws Exception {
        // Given
        User minimalUser = User.builder()
                .name("Test")
                .lastName("User")
                .email("test@example.com")
                .password("pass")
                .address("Address")
                .phoneNumber("123456")
                .zipCode(12345)
                .taxIdCode("TESTCODE")
                .birthDate(LocalDate.now())
                .birthPlace("Place")
                .city("City")
                .province("PR")
                .authUserId("auth-test")
                .build();

        User savedUser = User.builder()
                .uid("new-uid-456")
                .name("Test")
                .lastName("User")
                .email("test@example.com")
                .password("pass")
                .address("Address")
                .phoneNumber("123456")
                .zipCode(12345)
                .taxIdCode("TESTCODE")
                .birthDate(LocalDate.now())
                .birthPlace("Place")
                .city("City")
                .province("PR")
                .authUserId("auth-test")
                .build();

        when(userService.createUser(any(User.class))).thenReturn(savedUser);

        // When & Then
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(minimalUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uid").value("new-uid-456"))
                .andExpect(jsonPath("$.name").value("Test"));

        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    void testCreateUserWithSubscriptionType() throws Exception {
        // Given
        User userWithSubscription = testUser;
        userWithSubscription.setSubscriptionType(SubscriptionType.SILVER);

        when(userService.createUser(any(User.class))).thenReturn(userWithSubscription);

        // When & Then
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userWithSubscription)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subscriptionType").value("SILVER"));

        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    void testDeleteUserNotFound() throws Exception {
        // Given
        String uid = "non-existent-uid";
        doNothing().when(userService).deleteUser(uid);

        // When & Then
        mockMvc.perform(delete("/user/{uid}", uid))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(uid);
    }
}


