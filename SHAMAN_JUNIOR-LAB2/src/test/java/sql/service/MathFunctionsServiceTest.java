package sql.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import sql.repositories.MathFunctionsRepository;


@SpringBootTest
@Sql(scripts = "/sql/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MathFunctionsServiceTest {

    @Autowired
    private MathFunctionsService mathFunctionService;

    @Autowired
    private MathFunctionsRepository mathFunctionRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        // Дополнительные настройки перед каждым тестом, если нужны
    }

    @AfterEach
    public void cleanup() {
        // Очистка или дополнительные действия после каждого теста
    }

    @Test
    public void testCreateFunction() {
    }
}
