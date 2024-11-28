package web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import web.DTO.MathFunctionsDTO;
import web.config.TestSecurityConfig;
import web.controllers.MathFunctionsContoreller;
import web.service.MathFunctionsService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test") // Указываем, что тесты работают в профиле "test"
@AutoConfigureMockMvc
public class MathFunctionsContorellerTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private MathFunctionsService mathFunctionsService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testCreateFunction() throws Exception {
        MathFunctionsDTO inputDto = new MathFunctionsDTO();
        MathFunctionsDTO outputDto = new MathFunctionsDTO();

        when(mathFunctionsService.create(any())).thenReturn(outputDto);

        mockMvc.perform(post("/functions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDto)))
                .andExpect(status().isOk()) // Ожидаем успешный статус
                .andExpect(content().json(new ObjectMapper().writeValueAsString(outputDto))); // Проверяем, что ответ совпадает с ожидаемым
    }


}