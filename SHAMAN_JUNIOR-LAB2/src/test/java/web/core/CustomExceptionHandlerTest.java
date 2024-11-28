package web.core;

import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomExceptionHandlerTest {


    @InjectMocks
    private CustomExceptionHandler customExceptionHandler;

    @Mock
    private Model model;

    @Mock
    private HttpServletRequest request;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private LoadFunctionExecption loadFunctionExecption;

    @Mock
    private ArrayIsNotSortedException arrayIsNotSortedException;

    @Mock
    private DifferentLengthOfArraysException differentLengthOfArraysException;

    @Mock
    private InconsistentFunctionsException inconsistentFunctionsException;

    @Mock
    private RemoveIncorrectPoint removeIncorrectPoint;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleArrayIsNotSortedException() {
        // Подготовка данных
        String expectedMessage = "Array is not sorted";
        when(arrayIsNotSortedException.getMessage()).thenReturn(expectedMessage);
        when(request.getParameter("redirectTarget")).thenReturn("someTarget");

        // Вызов метода
        String result = customExceptionHandler.handleArrayIsNotSortedException(arrayIsNotSortedException, model, request, redirectAttributes);

        // Проверка
        verify(redirectAttributes).addAttribute("errorMessage", expectedMessage);
        verify(redirectAttributes).addAttribute("showError", true);
        verify(redirectAttributes).addAttribute("redirectTarget", "someTarget");
        assertEquals("redirect:/someTarget", result);
    }

    @Test
    public void testHandleDifferentLengthOfArraysException() {
        // Подготовка данных
        String expectedMessage = "Arrays have different lengths";
        when(differentLengthOfArraysException.getMessage()).thenReturn(expectedMessage);
        when(request.getRequestURI()).thenReturn("/tabulated-function-array/create");

        // Вызов метода
        String result = customExceptionHandler.handleDifferentLengthOfArraysException(differentLengthOfArraysException, model, request);

        // Проверка
        verify(model).addAttribute("errorTitle", "Ошибка");
        verify(model).addAttribute("errorMessage", expectedMessage);
    }

    @Test
    public void testHandleLoadFunctionExecption() {
        // Подготовка данных
        String expectedMessage = "Error loading function";
        when(loadFunctionExecption.getMessage()).thenReturn(expectedMessage);

        // Вызов метода
        ResponseEntity<Map<String, String>> responseEntity = customExceptionHandler.handleLoadFunctionExecption(loadFunctionExecption, model, request);

        // Проверка
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Map<String, String> body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("true", body.get("showError"));
        assertEquals(expectedMessage, body.get("error"));
    }

    @Test
    public void testHandleInconsistentFunctionsException() {
        // Подготовка данных
        String expectedMessage = "Inconsistent functions error";
        when(inconsistentFunctionsException.getMessage()).thenReturn(expectedMessage);
        when(request.getParameter("redirectTarget")).thenReturn("tabulated-operations");

        // Вызов метода
        String result = customExceptionHandler.handleInconsistentFunctionsException(inconsistentFunctionsException, model, request, redirectAttributes);

        // Проверка
        verify(redirectAttributes).addAttribute("errorMessage", expectedMessage);
        verify(redirectAttributes).addAttribute("showError", true);
        assertEquals("redirect:/tabulated-operations", result);
    }

    @Test
    public void testHandleRemoveIncorrectPoint() {
        // Подготовка данных
        String expectedMessage = "Point to remove is incorrect";
        when(removeIncorrectPoint.getMessage()).thenReturn(expectedMessage);

        // Вызов метода
        ResponseEntity<Map<String, String>> responseEntity = customExceptionHandler.handleRemoveIncorrectPoint(removeIncorrectPoint, model, request, redirectAttributes);

        // Проверка
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Map<String, String> body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("true", body.get("showError"));
        assertEquals(expectedMessage, body.get("error"));
    }
}