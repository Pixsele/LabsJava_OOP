package exceptions;

import exceptions.ArrayIsNotSortedException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArrayIsNotSortedExceptionTest {

    // Тест конструктора без параметров
    @Test
    void testArrayIsNotSortedExceptionWithoutMessage() {
        ArrayIsNotSortedException exception = assertThrows(
                ArrayIsNotSortedException.class,
                () -> { throw new ArrayIsNotSortedException(); }
        );
        assertNull(exception.getMessage(), "Сообщение должно быть null для конструктора без параметров.");
    }

    // Тест конструктора с сообщением
    @Test
    void testArrayIsNotSortedExceptionWithMessage() {
        String expectedMessage = "Array is not sorted!";
        ArrayIsNotSortedException exception = assertThrows(
                ArrayIsNotSortedException.class,
                () -> { throw new ArrayIsNotSortedException(expectedMessage); }
        );
        assertEquals(expectedMessage, exception.getMessage(), "Сообщение должно совпадать с переданным.");
    }
}
