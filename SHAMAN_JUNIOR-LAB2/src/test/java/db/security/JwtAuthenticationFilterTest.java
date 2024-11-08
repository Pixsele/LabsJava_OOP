package db.security;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);  // Инициализация моков
    }

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.clearContext();  // Очищаем SecurityContext после каждого теста
    }

    @Test
    public void testDoFilterInternal_ValidToken_ShouldSetAuthentication() throws ServletException, IOException {
        String token = "valid_token";
        String username = "user";
        Role role = Role.USER;  // Пример роли
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        // Мокаем поведение jwtUtil
        when(jwtUtil.isTokenExpired(token)).thenReturn(false);
        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(jwtUtil.extractRole(token)).thenReturn(role);

        // Мокаем заголовок запроса
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        // Вызываем фильтр
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Проверяем, что аутентификация была установлена в SecurityContext
        verify(filterChain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(username, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        assertTrue(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(authority));
    }

    @Test
    public void testDoFilterInternal_InvalidToken_ShouldNotSetAuthentication() throws ServletException, IOException {
        String token = "invalid_token";

        // Мокаем поведение jwtUtil
        when(jwtUtil.isTokenExpired(token)).thenReturn(true);

        // Мокаем заголовок запроса
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        // Вызываем фильтр
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Проверяем, что аутентификация не была установлена
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
