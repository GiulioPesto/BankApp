package com.giuliopastore.BankApp.entities.role;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void testRoleCreation() {
        // Given & When
        Role role = new Role();
        role.setUid("role-uid-123");
        role.setName("ADMIN");
        role.setDescription("Administrator role with full access");

        // Then
        assertNotNull(role);
        assertEquals("role-uid-123", role.getUid());
        assertEquals("ADMIN", role.getName());
        assertEquals("Administrator role with full access", role.getDescription());
    }

    @Test
    void testRoleAllArgsConstructor() {
        // Given & When
        Role role = new Role(
                "role-uid-456",
                "USER",
                "Standard user role"
        );

        // Then
        assertNotNull(role);
        assertEquals("role-uid-456", role.getUid());
        assertEquals("USER", role.getName());
        assertEquals("Standard user role", role.getDescription());
    }

    @Test
    void testRoleNoArgsConstructor() {
        // When
        Role role = new Role();

        // Then
        assertNotNull(role);
        assertNull(role.getUid());
        assertNull(role.getName());
        assertNull(role.getDescription());
    }

    @Test
    void testRoleGettersAndSetters() {
        // Given
        Role role = new Role();

        // When
        role.setUid("role-789");
        role.setName("MANAGER");
        role.setDescription("Manager role with limited admin access");

        // Then
        assertEquals("role-789", role.getUid());
        assertEquals("MANAGER", role.getName());
        assertEquals("Manager role with limited admin access", role.getDescription());
    }

    @Test
    void testMultipleRoleTypes() {
        // Given & When
        Role adminRole = new Role("admin-uid", "ADMIN", "Full access");
        Role userRole = new Role("user-uid", "USER", "Basic access");
        Role managerRole = new Role("manager-uid", "MANAGER", "Moderate access");

        // Then
        assertNotEquals(adminRole.getUid(), userRole.getUid());
        assertNotEquals(adminRole.getName(), userRole.getName());
        assertNotEquals(userRole.getUid(), managerRole.getUid());
    }

    @Test
    void testRoleWithEmptyDescription() {
        // Given & When
        Role role = new Role("role-uid", "GUEST", "");

        // Then
        assertEquals("", role.getDescription());
    }

    @Test
    void testRoleWithLongDescription() {
        // Given
        String longDescription = "This is a very long description for a role that might contain " +
                "extensive information about the permissions and responsibilities " +
                "associated with this particular role in the system.";

        // When
        Role role = new Role("role-uid", "SUPERVISOR", longDescription);

        // Then
        assertEquals(longDescription, role.getDescription());
        assertTrue(role.getDescription().length() > 100);
    }
}

