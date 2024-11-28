package web.controllers;

import function.api.MathFunction;
import function.api.TabulatedFunction;
import function.factory.ArrayTabulatedFunctionFactory;
import function.factory.TabulatedFunctionFactory;
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

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class TabulatedFunctionMathControllerTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private FunctionRepository functionRepository;

    @MockBean
    private MathFunctionsService mathFunctionsService;

    @Autowired
    private MockMvc mockMvc;

    private TabulatedFunctionMathController controller;
    private HttpSession session;
    private Model model;

    @BeforeEach
    public void setUp() {
        controller = new TabulatedFunctionMathController(functionRepository);
        session = mock(HttpSession.class);
        model = mock(Model.class);
        functionRepository = mock(FunctionRepository.class);
        controller.functionRepository = functionRepository; // Инъекция зависимостей
    }

    @Test
    public void testShowModalFormWithFactoryInSession() throws Exception {
        TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        Map<String, MathFunction> mockMap = new HashMap<>();
        mockMap.put("sin", mock(MathFunction.class));

        when(session.getAttribute("FACTORY_KEY")).thenReturn(factory);
        when(functionRepository.getFunctionMap()).thenReturn(mockMap);

        String result = controller.showModalForm("redirectTarget", "target", model, session);

        verify(model).addAttribute("factory", "ArrayTabulatedFunctionFactory");
        verify(model).addAttribute("functionMap", mockMap);
        verify(model).addAttribute("redirectTarget", "redirectTarget");
        verify(model).addAttribute("target", "target");

        assertEquals("fragments/modalFormMathFunc", result);
    }

    @Test
    public void testShowModalFormWithDefaultFactory() throws Exception {
        when(session.getAttribute("FACTORY_KEY")).thenReturn(null);

        String result = controller.showModalForm("redirectTarget", "target", model, session);

        verify(model).addAttribute("factory", "ArrayTabulatedFunctionFactory");
        assertEquals("fragments/modalFormMathFunc", result);
    }

    @Test
    public void testCreateModalFunctionSuccess() {
        String functionName = "sin";
        double xFrom = 0.0;
        double xTo = 10.0;
        int count = 5;
        String redirectTarget = "tabulated-operations";
        String target = "test";

        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);
        FunctionRepository functionRepository = mock(FunctionRepository.class);
        TabulatedFunctionFactory factory = mock(TabulatedFunctionFactory.class);
        MathFunction mathFunction = mock(MathFunction.class);
        TabulatedFunction tabulatedFunction = mock(TabulatedFunction.class);

        TabulatedFunctionMathController controller = new TabulatedFunctionMathController(functionRepository);
        controller.functionRepository = functionRepository;
        controller.tableFunctionFactory = factory;

        when(functionRepository.getFunction(functionName)).thenReturn(mathFunction);
        when(factory.create(mathFunction, xFrom, xTo, count)).thenReturn(tabulatedFunction);

        String result = controller.createModalFunction(functionName, xFrom, xTo, count, redirectTarget, target, model, session);

        verify(session).setAttribute(target + "Func", tabulatedFunction);
        assertEquals("redirect:/tabulated-operations", result);
    }

    @Test
    public void testCreateModalFunctionWithInvalidFunctionName() {
        String functionName = "invalid";
        double xFrom = 0.0;
        double xTo = 10.0;
        int count = 5;
        String redirectTarget = "tabulated-operations";
        String target = "test";

        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);
        FunctionRepository functionRepository = mock(FunctionRepository.class);
        TabulatedFunctionFactory factory = mock(TabulatedFunctionFactory.class);

        TabulatedFunctionMathController controller = new TabulatedFunctionMathController(functionRepository);
        controller.functionRepository = functionRepository;
        controller.tableFunctionFactory = factory;

        when(functionRepository.getFunction(functionName)).thenReturn(null);

    }

}