package exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoadFunctionExecptionTest {

    @Test
    public void testLoadFunctionExecptionConstructor() {
        LoadFunctionExecption exception = new LoadFunctionExecption();
        assertNotNull(exception);
        assertNull(exception.getMessage());

        String expectedMessage = "Ошибка загрузки функции";
        LoadFunctionExecption exceptionWithMessage = new LoadFunctionExecption(expectedMessage);
        assertNotNull(exceptionWithMessage);
        assertEquals(expectedMessage, exceptionWithMessage.getMessage());
    }

    @Test
    public void testRemoveIncorrectPointConstructor() {

        RemoveIncorrectPoint exception = new RemoveIncorrectPoint();
        assertNotNull(exception);
        assertNull(exception.getMessage());

        // Проверяем конструктор с сообщением
        String expectedMessage = "Некорректная точка для удаления";
        RemoveIncorrectPoint exceptionWithMessage = new RemoveIncorrectPoint(expectedMessage);
        assertNotNull(exceptionWithMessage);
        assertEquals(expectedMessage, exceptionWithMessage.getMessage());
    }
}