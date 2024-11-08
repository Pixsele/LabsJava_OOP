package db.security;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SecurityConfigTest {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Test
    void testAuthenticationManagerBean() {
        assertNotNull(authenticationManager, "AuthenticationManager bean должен быть создан и не быть null");
    }
}
