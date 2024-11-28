package web.controllers;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class EditAndRemoveControllerTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private FunctionRepository functionRepository;

    @MockBean
    private MathFunctionsService mathFunctionsService;

    @Autowired
    private MockMvc mockMvc;

    private EditAndRemoveController controller;
    private HttpSession session;
    private Model model;
    private TabulatedFunction mockFunction;

    @BeforeEach
    public void setUp() {
        controller = new EditAndRemoveController();
        session = mock(HttpSession.class);
        model = mock(Model.class);
        mockFunction = mock(TabulatedFunction.class);
    }

    @Test
    public void testEdit() {
        String saveTarget = "test";
        double x = 1.0;
        double y = 2.0;

        when(session.getAttribute(saveTarget + "Func")).thenReturn(mockFunction);

        // Вызов метода
        String result = controller.edit(saveTarget, x, y, model, session);

        // Проверки
        verify(session).getAttribute(saveTarget + "Func");
        verify(mockFunction).insert(x, y);
        verify(session).setAttribute(saveTarget + "Func", mockFunction);

        assertEquals("redirect:/tabulated-operations", result);
    }

    @Test
    public void testRemoveSuccess() {
        String saveTarget = "test";
        double x = 1.0;
        String redirectTarget = "somePage";

        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);
        TabulatedFunction mockFunction = mock(TabulatedFunction.class);

        when(session.getAttribute(saveTarget + "Func")).thenReturn(mockFunction);
        when(mockFunction.indexOfX(x)).thenReturn(0); // Точка найдена

        EditAndRemoveController controller = new EditAndRemoveController();
        String result = controller.remove(saveTarget, x, redirectTarget, model, session);

        // Проверки
        verify(session).getAttribute(saveTarget + "Func");
        verify(mockFunction).indexOfX(x);
        verify(mockFunction).remove(0);
        verify(session).setAttribute(saveTarget + "Func", mockFunction);
        verify(model).addAttribute("redirectTarget", redirectTarget);

        assertEquals("redirect:/tabulated-operations", result);
    }



}