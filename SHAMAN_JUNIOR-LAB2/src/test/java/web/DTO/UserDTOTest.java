package web.DTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.DTO.UserDTO;
import web.security.Role;

import static org.junit.jupiter.api.Assertions.*;

public class UserDTOTest {

    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        userDTO = new UserDTO();
    }

    @Test
    public void testSetAndGetUsername() {
        userDTO.setUsername("testUser");
        assertEquals("testUser", userDTO.getUsername());
    }

    @Test
    public void testSetAndGetPassword() {
        userDTO.setPassword("testPassword");
        assertEquals("testPassword", userDTO.getPassword());
    }

    @Test
    public void testSetAndGetRole() {
        Role role = Role.ADMIN; // assuming you have an enum Role with ADMIN as a possible value
        userDTO.setRole(role);
        assertEquals(role, userDTO.getRole());
    }

    @Test
    public void testEmptyUserDTO() {
        assertNull(userDTO.getUsername());
        assertNull(userDTO.getPassword());
        assertNull(userDTO.getRole());
    }

    @Test
    public void testFullUserDTO() {
        Role role = Role.USER; // assuming Role enum has USER as well
        userDTO.setUsername("user1");
        userDTO.setPassword("password123");
        userDTO.setRole(role);

        assertEquals("user1", userDTO.getUsername());
        assertEquals("password123", userDTO.getPassword());
        assertEquals(role, userDTO.getRole());
    }
}
