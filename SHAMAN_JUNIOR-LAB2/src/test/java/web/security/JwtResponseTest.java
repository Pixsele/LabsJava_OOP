package web.security;

import org.junit.jupiter.api.Test;

public class JwtResponseTest {

    @Test
    public void testJwtResponseConstructor() {
        String expectedToken = "test_token";
        JwtResponse response = new JwtResponse(expectedToken);

        // Проверяем, что конструктор правильно инициализирует токен
        assertEquals(expectedToken, response.getToken());
    }

    @Test
    public void testJwtResponseSetterGetter() {
        JwtResponse response = new JwtResponse();
        String expectedToken = "another_test_token";

        // Устанавливаем токен с помощью сеттера
        response.setToken(expectedToken);

        // Проверяем, что геттер возвращает правильное значение
        assertEquals(expectedToken, response.getToken());
    }
}