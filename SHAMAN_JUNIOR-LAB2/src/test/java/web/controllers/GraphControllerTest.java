package web.controllers;

import database.models.MathFunctionsEntity;
import database.repositories.MathFunctionsRepository;
import function.api.TabulatedFunction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import web.DTO.MathFunctionsDTO;
import web.service.MathFunctionsService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class GraphControllerTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private MathFunctionsService mathFunctionsService;

    @MockBean
    private MathFunctionsRepository mathFunctionsRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGraph() throws Exception {
        // Мокаем репозиторий функций
        MathFunctionsDTO dto1 = new MathFunctionsDTO();
        dto1.setName("function1");
        List<MathFunctionsEntity> functions = new ArrayList<>();
        functions.add(new MathFunctionsEntity());
        when(mathFunctionsRepository.findAll()).thenReturn(functions);


        mockMvc.perform(get("/graph"))
                .andExpect(status().isOk())
                .andExpect(view().name("graph"))
                .andExpect(model().attributeExists("functions"))
                .andExpect(model().attribute("functions", functions));
    }

    @Test
    public void testApply() throws Exception {
        TabulatedFunction mockFunction = mock(TabulatedFunction.class);
        when(mockFunction.apply(anyDouble())).thenReturn(42.0);

        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getAttribute("graphFunc")).thenReturn(mockFunction);

        mockMvc.perform(post("/graph/apply")
                        .param("xToApply", "5.0")
                        .sessionAttr("graphFunc", mockFunction))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/graph"));

    }

    @Test
    public void testGraphWithError() throws Exception {
        String errorMessage = "An error occurred";

        // Мокаем репозиторий функций
        MathFunctionsDTO dto1 = new MathFunctionsDTO();
        dto1.setName("function1");
        List<MathFunctionsEntity> functions = new ArrayList<>();
        functions.add(new MathFunctionsEntity());
        when(mathFunctionsRepository.findAll()).thenReturn(functions);

        // Эмуляция запроса GET с ошибкой
        mockMvc.perform(get("/graph")
                        .param("showError", "true")
                        .param("errorMessage", errorMessage))
                .andExpect(status().isOk())
                .andExpect(view().name("graph"))
                .andExpect(model().attributeExists("showError"))
                .andExpect(model().attribute("showError", true))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", errorMessage));
    }

}