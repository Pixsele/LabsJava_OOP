package web.controllers;

import concurrent.IntegralTaskExecutor;
import database.models.MathFunctionsEntity;
import database.repositories.MathFunctionsRepository;
import function.api.TabulatedFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import web.core.FunctionRepository;
import web.service.MathFunctionsService;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class IntegralOperatorControllerTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private FunctionRepository functionRepository;

    @MockBean
    private MathFunctionsService mathFunctionsService;

    @Autowired
    private MockMvc mockMvc;

    private IntegralOperatorController controller;
    private HttpSession session;
    private Model model;
    private MathFunctionsRepository mathFunctionsRepository;
    private TabulatedFunction mockFunction;

    @BeforeEach
    public void setUp() {
        mathFunctionsRepository = mock(MathFunctionsRepository.class);
        controller = new IntegralOperatorController();
        controller.mathFunctionsRepository = mathFunctionsRepository;
        session = mock(HttpSession.class);
        model = mock(Model.class);
        mockFunction = mock(TabulatedFunction.class);
    }

    @Test
    public void testIntegralWithAttributes() {
        when(session.getAttribute("integralFunc")).thenReturn(mockFunction);
        when(mockFunction.getCount()).thenReturn(5);
        List<MathFunctionsEntity> listOfFunctions = new ArrayList<>();
        listOfFunctions.add(0,new MathFunctionsEntity());

        when(mathFunctionsRepository.findAll()).thenReturn(listOfFunctions);

        String result = controller.integral(model, session, "true", "An error occurred");

        verify(model).addAttribute("showError", "true");
        verify(model).addAttribute("errorMessage", "An error occurred");
        verify(model).addAttribute("countOfIntegral", 5);


        assertEquals("integral-operation", result);
    }

    @Test
    public void testIntegralWithoutAttributes() {
        when(session.getAttribute("integralFunc")).thenReturn(null);
        when(session.getAttribute("integralResult")).thenReturn(null);
        List<MathFunctionsEntity> listOfFunctions = new ArrayList<>();
        listOfFunctions.add(0,new MathFunctionsEntity());

        when(mathFunctionsRepository.findAll()).thenReturn(listOfFunctions);

        String result = controller.integral(model, session, null, null);

        verify(model).addAttribute("integralFunc", null);
        verify(model).addAttribute("integralResult", null);
        verify(session).getAttribute("integralFunc");
        verify(session).getAttribute("integralResult");

        assertEquals("integral-operation", result);
    }

    @Test
    public void testCalculate() throws ExecutionException, InterruptedException {
        int threadCount = 4;
        double expectedResult = 10.0;

        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);
        TabulatedFunction mockFunction = mock(TabulatedFunction.class);
        IntegralTaskExecutor mockExecutor = mock(IntegralTaskExecutor.class);

        when(session.getAttribute("integralFunc")).thenReturn(mockFunction);
        when(mockExecutor.itegrate(mockFunction)).thenReturn(expectedResult);

        IntegralOperatorController controller = new IntegralOperatorController();

        String result = controller.calculate(threadCount, model, session);

        verify(session).getAttribute("integralFunc");

        assertEquals("redirect:/integral", result);
    }

}