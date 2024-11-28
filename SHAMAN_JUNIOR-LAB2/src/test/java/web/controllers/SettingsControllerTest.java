package web.controllers;

import function.factory.ArrayTabulatedFunctionFactory;
import function.factory.LinkedListTabulatedFunctionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import web.core.FunctionRepository;
import web.service.MathFunctionsService;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class SettingsControllerTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private FunctionRepository functionRepository;

    @MockBean
    private MathFunctionsService mathFunctionsService;

    @Autowired
    private MockMvc mockMvc;

    private SettingsController controller;
    private HttpSession session;

    @BeforeEach
    public void setUp() {
        controller = new SettingsController();
        session = mock(HttpSession.class);
    }

    @Test
    public void testUpdateSettingsWithArrayFactory() {
        String factoryType = "array";

        String result = controller.updateSettings(factoryType, session);

        verify(session).setAttribute(eq("FACTORY_KEY"), any(ArrayTabulatedFunctionFactory.class));
        assertEquals("home", result);
    }

    @Test
    public void testUpdateSettingsWithLinkedListFactory() {
        String factoryType = "linkedlist";

        String result = controller.updateSettings(factoryType, session);

        verify(session).setAttribute(eq("FACTORY_KEY"), any(LinkedListTabulatedFunctionFactory.class));
        assertEquals("home", result);
    }

    @Test
    public void testUpdateSettingsWithInvalidFactoryType() {
        String factoryType = "invalid";

        String result = controller.updateSettings(factoryType, session);

        verify(session).setAttribute(eq("FACTORY_KEY"), isNull());
        assertEquals("home", result);
    }

    @Test
    public void testFactorySetCorrectlyForArray() {
        String factoryType = "array";

        controller.updateSettings(factoryType, session);

        verify(session).setAttribute(eq("FACTORY_KEY"), argThat(factory ->
                factory instanceof ArrayTabulatedFunctionFactory
        ));
    }

    @Test
    public void testFactorySetCorrectlyForLinkedList() {
        String factoryType = "linkedlist";

        controller.updateSettings(factoryType, session);

        verify(session).setAttribute(eq("FACTORY_KEY"), argThat(factory ->
                factory instanceof LinkedListTabulatedFunctionFactory
        ));
    }
}