package web.security;

import static org.junit.jupiter.api.Assertions.*;

import database.models.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;

class UserEntityTest {

    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setCreationTime(LocalDateTime.now());
    }

    @Test
    void testGetAuthoritiesWithRole() {
        user.setRole(Role.USER);  // Assuming you have an enum Role with USER and ADMIN

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("USER")));
    }

    @Test
    void testGetAuthoritiesWithoutRole() {
        user.setRole(null);

        assertNull(user.getRole());

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertNotNull(authorities);
        assertEquals(0, authorities.size());
    }

    @Test
    void testUsername() {
        assertEquals("testUser", user.getUsername());
    }

    @Test
    void testPassword() {
        assertEquals("testPassword", user.getPassword());
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(user.isEnabled());
    }

    @Test
    void testCreationTime() {
        assertNotNull(user.getCreationTime());
        assertTrue(user.getCreationTime().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void testId(){
        user.setId(1L);
        assertEquals(1L,user.getId());
    }
}
