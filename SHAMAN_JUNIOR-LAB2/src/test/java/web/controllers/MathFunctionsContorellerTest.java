package web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import web.DTO.MathFunctionsDTO;
import web.config.TestSecurityConfig;
import web.service.MathFunctionsService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(classes = {TestSecurityConfig.class}) // Указываем только тестовую конфигурацию
class MathFunctionsContorellerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateFunction() throws Exception {
        MathFunctionsDTO inputDto = new MathFunctionsDTO();
        MathFunctionsDTO outputDto = new MathFunctionsDTO();

        mockMvc.perform(post("/functions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(outputDto)));
    }

    @Test
    public void testReadFunction() throws Exception {
        long id = 1L;

        mockMvc.perform(get("/functions/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateFunction() throws Exception {
        long id = 1L;
        MathFunctionsDTO inputDto = new MathFunctionsDTO();

        mockMvc.perform(put("/functions/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFunction() throws Exception {
        long id = 1L;

        mockMvc.perform(delete("/functions/" + id))
                .andExpect(status().isOk());
    }
}
