package exceptions;

import exceptions.InterpolationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InterpolationExceptionTest {

    // Тест конструктора без параметров
    @Test
    void testInterpolationExceptionWithoutMessage() {
        InterpolationException exception = assertThrows(
                InterpolationException.class,
                () -> { throw new InterpolationException(); }
        );
        assertNull(exception.getMessage(), "Сообщение должно быть null для конструктора без параметров.");
    }

    // Тест конструктора с сообщением
    @Test
    void testInterpolationExceptionWithMessage() {
        String expectedMessage = "Interpolation failed!";
        InterpolationException exception = assertThrows(
                InterpolationException.class,
                () -> { throw new InterpolationException(expectedMessage); }
        );
        assertEquals(expectedMessage, exception.getMessage(), "Сообщение должно совпадать с переданным.");
    }
}
