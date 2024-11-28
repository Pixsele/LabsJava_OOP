package web.controllers;

import database.repositories.MathFunctionsRepository;
import function.SqrFunction;
import function.api.MathFunction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import web.core.FunctionRepository;
import web.service.MathFunctionsService;

import java.util.HashMap;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CompositeControllerTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private FunctionRepository functionRepository;

    @MockBean
    private MathFunctionsService mathFunctionsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCompositeControllerGET() throws Exception {

        Map<String, MathFunction> mathFunctions = new HashMap<>();
        mathFunctions.put("add", new SqrFunction());
        mathFunctions.put("sub", new SqrFunction());
        when(functionRepository.getFunctionMap()).thenReturn(mathFunctions);


        mockMvc.perform(MockMvcRequestBuilders.get("/composite"))
                .andExpect(status().isOk())
                .andExpect(view().name("composite"))
                .andExpect(model().attributeExists("functions"));
    }

    @Test
    void testCompositeControllerPOST() throws Exception {
        // Подготовка данных
        String firstFunc = "add";
        String secondFunc = "subtract";
        String compositeName = "compositeFunction";

        MathFunction first = new SqrFunction();
        MathFunction second = new SqrFunction();

        when(functionRepository.getFunction(firstFunc)).thenReturn(first);
        when(functionRepository.getFunction(secondFunc)).thenReturn(second);

        mockMvc.perform(MockMvcRequestBuilders.post("/composite/create")
                        .param("firstFunc", firstFunc)
                        .param("secondFunc", secondFunc)
                        .param("compositeName", compositeName))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/composite"));
    }
}