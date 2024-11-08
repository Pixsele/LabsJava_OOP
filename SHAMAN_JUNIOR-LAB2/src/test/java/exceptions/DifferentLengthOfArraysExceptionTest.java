package exceptions;

import exceptions.DifferentLengthOfArraysException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DifferentLengthOfArraysExceptionTest {

    // Тест конструктора без параметров
    @Test
    void testDifferentLengthOfArraysExceptionWithoutMessage() {
        DifferentLengthOfArraysException exception = assertThrows(
                DifferentLengthOfArraysException.class,
                () -> { throw new DifferentLengthOfArraysException(); }
        );
        assertNull(exception.getMessage(), "Сообщение должно быть null для конструктора без параметров.");
    }

    // Тест конструктора с сообщением
    @Test
    void testDifferentLengthOfArraysExceptionWithMessage() {
        String expectedMessage = "Arrays have different lengths!";
        DifferentLengthOfArraysException exception = assertThrows(
                DifferentLengthOfArraysException.class,
                () -> { throw new DifferentLengthOfArraysException(expectedMessage); }
        );
        assertEquals(expectedMessage, exception.getMessage(), "Сообщение должно совпадать с переданным.");
    }
}
