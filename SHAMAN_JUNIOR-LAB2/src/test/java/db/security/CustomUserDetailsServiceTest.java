package db.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_UserExists_ReturnsUser() {
        // Данные для теста
        String username = "testuser";
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword("encodedPassword");

        // Настройка заглушки
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Вызов тестируемого метода
        UserDetails result = customUserDetailsService.loadUserByUsername(username);

        // Проверка результата
        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsException() {
        // Данные для теста
        String username = "nonexistentuser";

        // Настройка заглушки
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Проверка исключения
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(username));
    }

    @Test
    void saveUser_EncodesPasswordAndSavesUser() {
        // Данные для теста
        UserEntity user = new UserEntity();
        user.setUsername("newuser");
        user.setPassword("rawPassword");

        // Настройка заглушки
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");

        // Вызов тестируемого метода
        customUserDetailsService.saveUser(user);

        // Проверка
        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }
}
