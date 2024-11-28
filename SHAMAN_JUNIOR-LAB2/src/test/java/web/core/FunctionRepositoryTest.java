package web.core;

import function.api.MathFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FunctionRepositoryTest {
    private FunctionRepository functionRepository;
    private MathFunction mockMathFunction;
    private AnnotatedFunctions mockAnnotatedFunction;

    @BeforeEach
    public void setUp() throws Exception {
        // Мокаем MathFunction и AnnotatedFunctions для тестов
        mockMathFunction = mock(MathFunction.class);
        mockAnnotatedFunction = mock(AnnotatedFunctions.class);

        // Создаем экземпляр FunctionRepository для тестирования
        functionRepository = new FunctionRepository();

        // Мокаем функции для статической загрузки
        when(mockAnnotatedFunction.getLocalizedName()).thenReturn("mockFunction");
        when(mockAnnotatedFunction.getFunction()).thenReturn(mockMathFunction);

        // Задаем функции в репозиторий
        functionRepository.addUserFunction(mockAnnotatedFunction);
    }

    @Test
    public void testAddUserFunction() {
        // Проверяем, что пользовательская функция добавляется в список
        functionRepository.addUserFunction(mockAnnotatedFunction);

        assertTrue(functionRepository.getAllFunctions().contains(mockAnnotatedFunction));
    }

    @Test
    public void testGetFunctionByName() {
        // Добавляем функцию в репозиторий
        functionRepository.addUserFunction(mockAnnotatedFunction);

        // Проверяем, что функция по имени возвращается корректно
        MathFunction result = functionRepository.getFunction("mockFunction");

        assertNotNull(result);
        assertEquals(mockMathFunction, result);
    }

    @Test
    public void testGetFunctionMap() {
        // Добавляем функцию в репозиторий
        functionRepository.addUserFunction(mockAnnotatedFunction);

        // Проверяем, что карта функций содержит нужные функции
        Map<String, MathFunction> functionMap = functionRepository.getFunctionMap();

        assertTrue(functionMap.containsKey("mockFunction"));
        assertEquals(mockMathFunction, functionMap.get("mockFunction"));
    }

    @Test
    public void testGetAllFunctions() {
        // Добавляем функции
        functionRepository.addUserFunction(mockAnnotatedFunction);

        // Проверяем, что возвращаемый список содержит все функции
        List<AnnotatedFunctions> allFunctions = functionRepository.getAllFunctions();

        assertTrue(allFunctions.contains(mockAnnotatedFunction));
    }

    @Test
    public void testGetFunctionNotFound() {
        // Пытаемся получить несуществующую функцию
        MathFunction result = functionRepository.getFunction("nonExistentFunction");

        // Проверяем, что результат равен null
        assertNull(result);
    }
}