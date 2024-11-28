package web.controllers;

import database.repositories.MathFunctionsRepository;
import function.api.TabulatedFunction;
import function.factory.TabulatedFunctionFactory;
import operations.TabulatedFunctionOperationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class TabulatedOperationsControllerTest {


    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private FunctionRepository functionRepository;

    @MockBean
    private MathFunctionsService mathFunctionsService;

    @MockBean
    private TabulatedFunctionOperationService service;

    @Autowired
    private MockMvc mockMvc;

    private TabulatedOperationsController controller;
    private HttpSession session;
    private Model model;
    private MathFunctionsRepository mathFunctionsRepository;

    @BeforeEach
    public void setUp() {
        controller = new TabulatedOperationsController();
        session = mock(HttpSession.class);
        model = mock(Model.class);
        mathFunctionsRepository = mock(MathFunctionsRepository.class);
        controller.mathFunctionsRepository = mathFunctionsRepository;
    }

    @Test
    public void testShowFormWithError() {
        boolean showError = true;
        String errorMessage = "Error occurred";
        String redirectTarget = "targetPage";

        String result = controller.showForm(model, session, showError, errorMessage, redirectTarget);

        verify(model).addAttribute("showError", showError);
        verify(model).addAttribute("errorMessage", errorMessage);
        verify(model).addAttribute("redirectTarget", redirectTarget);
        assertEquals("tabulated-operations", result);
    }

    @Test
    public void testShowFormWithOperands() {
        TabulatedFunction operand1 = mock(TabulatedFunction.class);
        TabulatedFunction operand2 = mock(TabulatedFunction.class);

        when(session.getAttribute("operand1Func")).thenReturn(operand1);
        when(session.getAttribute("operand2Func")).thenReturn(operand2);
        when(operand1.getCount()).thenReturn(5);
        when(operand2.getCount()).thenReturn(3);

        String result = controller.showForm(model, session, false, null, null);

        verify(model).addAttribute("operand1Func", operand1);
        verify(model).addAttribute("count1", 5);
        verify(model).addAttribute("operand2Func", operand2);
        verify(model).addAttribute("count2", 3);
        assertEquals("tabulated-operations", result);
    }

    @Test
    public void testShowFormWithoutOperands() {
        String result = controller.showForm(model, session, false, null, null);

        verify(model).addAttribute("operand1Func", null);
        verify(model).addAttribute("operand2Func", null);
        assertEquals("tabulated-operations", result);
    }


    @Test
    public void testDoOperationWithoutOperands() {
        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);

        TabulatedOperationsController controller = new TabulatedOperationsController();

        when(session.getAttribute("operand1Func")).thenReturn(null);
        when(session.getAttribute("operand2Func")).thenReturn(null);

        String result = controller.doOperation("add", model, session);

        assertEquals("redirect:/tabulated-operations", result);
    }
}