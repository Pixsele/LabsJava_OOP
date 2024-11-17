package web.security;

import static org.junit.jupiter.api.Assertions.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    void generateToken_ShouldCreateValidToken() {
        // Данные для теста
        String username = "testuser";
        Role role = Role.USER;

        // Генерация токена
        String token = jwtUtil.generateToken(username, role);

        // Проверка, что токен не пустой
        assertNotNull(token);

        // Проверка, что токен имеет требуемые данные (username, role, expiration)
        Claims claims = Jwts.parser()
                .setSigningKey(jwtUtil.getKey())
                .parseClaimsJws(token)
                .getBody();

        assertEquals(username, claims.getSubject());
        assertEquals(role.name(), claims.get("role"));
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    void extractUsername_ShouldReturnCorrectUsername() {
        // Данные для теста
        String username = "testuser";
        Role role = Role.USER;

        // Генерация токена
        String token = jwtUtil.generateToken(username, role);

        // Извлечение имени пользователя
        String extractedUsername = jwtUtil.extractUsername(token);

        // Проверка правильности извлечения
        assertEquals(username, extractedUsername);
    }

    @Test
    void extractRole_ShouldReturnCorrectRole() {
        // Данные для теста
        String username = "testuser";
        Role role = Role.USER;

        // Генерация токена
        String token = jwtUtil.generateToken(username, role);

        // Извлечение роли
        Role extractedRole = jwtUtil.extractRole(token);

        // Проверка правильности извлечения роли
        assertEquals(role, extractedRole);
    }



    @Test
    void isTokenExpired_ShouldReturnFalseWhenNotExpired() {
        // Данные для теста
        String username = "testuser";
        Role role = Role.USER;

        // Генерация токена
        String token = jwtUtil.generateToken(username, role);

        // Проверка, что токен не истек
        assertFalse(jwtUtil.isTokenExpired(token));
    }
}
