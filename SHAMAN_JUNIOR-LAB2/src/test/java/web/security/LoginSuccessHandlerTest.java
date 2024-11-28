package web.security;

import function.factory.ArrayTabulatedFunctionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import web.core.FunctionRepository;
import web.service.MathFunctionsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class LoginSuccessHandlerTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private FunctionRepository functionRepository;

    @MockBean
    private MathFunctionsService mathFunctionsService;

    @Autowired
    private MockMvc mockMvc;

    private LoginSuccessHandler loginSuccessHandler;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Authentication authentication;
    private HttpSession session;



    @BeforeEach
    public void setUp() {
        loginSuccessHandler = new LoginSuccessHandler();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        authentication = mock(Authentication.class);
        session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testOnAuthenticationSuccess_NoFactoryInSession() throws IOException, ServletException {
        when(session.getAttribute("FACTORY_KEY")).thenReturn(null);

        loginSuccessHandler.onAuthenticationSuccess(request, response, authentication);

        verify(response).sendRedirect("/");
    }

    @Test
    public void testOnAuthenticationSuccess_FactoryInSession() throws IOException, ServletException {
        when(session.getAttribute("FACTORY_KEY")).thenReturn(new ArrayTabulatedFunctionFactory());

        loginSuccessHandler.onAuthenticationSuccess(request, response, authentication);

        verify(session, never()).setAttribute("FACTORY_KEY", new ArrayTabulatedFunctionFactory());

        verify(response).sendRedirect("/");
    }

}