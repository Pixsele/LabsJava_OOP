package web.controllers;

import function.api.TabulatedFunction;
import function.factory.ArrayTabulatedFunctionFactory;
import function.factory.TabulatedFunctionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import web.core.FunctionRepository;
import web.service.MathFunctionsService;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class СreateFunctionArrayOrLinkedModalControllerTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private FunctionRepository functionRepository;

    @MockBean
    private MathFunctionsService mathFunctionsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HttpSession session;

    @Test
    public void testCreateForm() throws Exception {
        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("FACTORY_KEY", new ArrayTabulatedFunctionFactory()); // Пример с фабрикой

        mockMvc.perform(MockMvcRequestBuilders.get("/arrayFunc/createForm")
                        .param("target", "operand1")
                        .param("redirectTarget", "someRedirectTarget")
                        .session(mockSession))
                .andExpect(MockMvcResultMatchers.view().name("fragments/createForm"))
                .andExpect(MockMvcResultMatchers.model().attribute("target", "operand1"))
                .andExpect(MockMvcResultMatchers.model().attribute("redirectTarget", "someRedirectTarget"))
                .andExpect(MockMvcResultMatchers.model().attribute("factory", "ArrayTabulatedFunctionFactory"));
    }

    @Test
    public void testGenerateTable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/arrayFunc/generateTable")
                        .param("points", "5")
                        .param("target", "operand1")
                        .param("redirectTarget", "someRedirectTarget"))
                .andExpect(MockMvcResultMatchers.view().name("fragments/tableForm"))
                .andExpect(MockMvcResultMatchers.model().attribute("points", 5))
                .andExpect(MockMvcResultMatchers.model().attribute("target", "operand1"))
                .andExpect(MockMvcResultMatchers.model().attribute("redirectTarget", "someRedirectTarget"));
    }

    @Test
    public void testCreateFunction() throws Exception {
        double[] x = {1.0, 2.0, 3.0};
        double[] y = {2.0, 4.0, 6.0};

        TabulatedFunctionFactory mockFactory = mock(TabulatedFunctionFactory.class);
        TabulatedFunction mockFunction = mock(TabulatedFunction.class);
        when(mockFactory.create(x, y)).thenReturn(mockFunction);

        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("FACTORY_KEY", mockFactory);

        mockMvc.perform(MockMvcRequestBuilders.post("/arrayFunc/createFunction")
                        .param("target", "operand1")
                        .param("redirectTarget", "someRedirectTarget")
                        .param("x", "1.0,2.0,3.0")
                        .param("y", "2.0,4.0,6.0")
                        .session(mockSession))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/someRedirectTarget"));

    }
}