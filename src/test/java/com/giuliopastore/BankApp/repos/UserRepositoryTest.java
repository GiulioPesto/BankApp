package com.giuliopastore.BankApp.repos;

import com.giuliopastore.BankApp.entities.user.User;
import com.giuliopastore.BankApp.enums.SubscriptionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindUserByUid() {
        // Given
        User user = createTestUser("test-uid-123");
        entityManager.persistAndFlush(user);

        // When
        User found = userRepository.findUserByUid("test-uid-123");

        // Then
        assertNotNull(found);
        assertEquals("test-uid-123", found.getUid());
        assertEquals("Mario", found.getName());
        assertEquals("Rossi", found.getLastName());
    }

    @Test
    void testFindUserByUidNotFound() {
        // When
        User found = userRepository.findUserByUid("non-existent-uid");

        // Then
        assertNull(found);
    }

    @Test
    void testSaveUser() {
        // Given
        User user = createTestUser("save-uid-123");

        // When
        User saved = userRepository.save(user);

        // Then
        assertNotNull(saved);
        assertEquals("save-uid-123", saved.getUid());
        assertEquals("Mario", saved.getName());

        // Verify it's in the database
        User found = entityManager.find(User.class, "save-uid-123");
        assertNotNull(found);
        assertEquals("Mario", found.getName());
    }

    @Test
    void testUpdateUser() {
        // Given
        User user = createTestUser("update-uid-123");
        entityManager.persistAndFlush(user);

        // When
        user.setName("UpdatedName");
        user.setLastName("UpdatedLastName");
        User updated = userRepository.save(user);

        // Then
        assertNotNull(updated);
        assertEquals("UpdatedName", updated.getName());
        assertEquals("UpdatedLastName", updated.getLastName());
    }

    @Test
    void testDeleteUser() {
        // Given
        User user = createTestUser("delete-uid-123");
        entityManager.persistAndFlush(user);

        // When
        userRepository.delete(user);
        entityManager.flush();

        // Then
        User found = entityManager.find(User.class, "delete-uid-123");
        assertNull(found);
    }

    @Test
    void testFindAll() {
        // Given
        User user1 = createTestUser("uid-1");
        User user2 = createTestUser("uid-2");
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.flush();

        // When
        var users = userRepository.findAll();

        // Then
        assertTrue(users.size() >= 2);
    }

    @Test
    void testSaveUserWithSubscriptionType() {
        // Given
        User user = createTestUser("premium-uid");
        user.setSubscriptionType(SubscriptionType.PREMIUM);

        // When
        User saved = userRepository.save(user);

        // Then
        assertNotNull(saved);
        assertEquals(SubscriptionType.PREMIUM, saved.getSubscriptionType());
    }

    @Test
    void testSaveUserWithSecondName() {
        // Given
        User user = createTestUser("second-name-uid");
        user.setSecondName("Giovanni");

        // When
        User saved = userRepository.save(user);

        // Then
        assertNotNull(saved);
        assertEquals("Giovanni", saved.getSecondName());
    }

    @Test
    void testSaveUserWithNullSecondName() {
        // Given
        User user = createTestUser("null-second-name-uid");
        user.setSecondName(null);

        // When
        User saved = userRepository.save(user);

        // Then
        assertNotNull(saved);
        assertNull(saved.getSecondName());
    }

    private User createTestUser(String uid) {
        return User.builder()
                .uid(uid)
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
                .authUserId("auth-" + uid)
                .build();
    }
}

