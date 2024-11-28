package web.controllers;

import database.repositories.MathFunctionsRepository;
import function.ArrayTabulatedFunction;
import function.api.TabulatedFunction;
import function.factory.ArrayTabulatedFunctionFactory;
import function.factory.TabulatedFunctionFactory;
import operations.TabulatedDifferentialOperator;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;
import web.core.FunctionRepository;
import web.service.MathFunctionsService;

import javax.servlet.http.HttpSession;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class DifferentialOperatorControllerTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private FunctionRepository functionRepository;

    @MockBean
    private MathFunctionsRepository mathFunctionsRepository;

    @MockBean
    private MathFunctionsService mathFunctionsService;

    @Mock
    private TabulatedFunctionFactory functionFactory;

    @InjectMocks
    private DifferentialOperatorController differentialOperatorController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testDifferentialOperatorGET() throws Exception {
        MockHttpSession session = new MockHttpSession();

        when(mathFunctionsRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/differential-operation")
                        .session(session)
                        .param("showError", "false"))
                .andExpect(status().isOk())
                .andExpect(view().name("differential-operation"))
                .andExpect(model().attributeExists("functions"))
                .andExpect(model().attribute("functions", Collections.emptyList()));
    }

    @Test
    public void testDoDiffOperator() {
        DifferentialOperatorController controller = new DifferentialOperatorController();
        HttpSession session= mock(HttpSession.class);
        Model model = mock(Model.class);
        TabulatedFunction mockFunction = mock(TabulatedFunction.class);
        TabulatedDifferentialOperator mockOperator = mock(TabulatedDifferentialOperator.class);
        TabulatedFunctionFactory mockFactory = mock(TabulatedFunctionFactory.class);


        double[] x = {1,2};
        double[] y = {1,2};
        TabulatedFunction derivedFunction = new ArrayTabulatedFunction(x,y);
        when(mockOperator.derive(mockFunction)).thenReturn(derivedFunction);

        TabulatedDifferentialOperator service = new TabulatedDifferentialOperator(mockFactory);
        TabulatedDifferentialOperator spyService = Mockito.spy(service);
        doReturn(derivedFunction).when(spyService).derive(mockFunction);

        when(session.getAttribute("diffFunc")).thenReturn(null);
        when(session.getAttribute("FACTORY_KEY")).thenReturn(mockFactory);

        String result = controller.doDiffOperator(model, session);

        verify(session).getAttribute("diffFunc");
        verify(session).getAttribute("FACTORY_KEY");

        assertEquals("redirect:/differential-operation", result);
    }

}