package concurrent;

import function.api.TabulatedFunction;
import function.ArrayTabulatedFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WriteTaskTest {
    private TabulatedFunction function;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        // Перенаправляем стандартный вывод для захвата вывода
        System.setOut(new PrintStream(outputStream));

        // Создаем табулированную функцию для теста
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {2, 4, 6, 8, 10};
        function = new ArrayTabulatedFunction(xValues, yValues);
    }

    @Test
    public void testRun() {
        double newValue = 42.0;
        WriteTask task = new WriteTask(newValue, function);
        Thread thread = new Thread(task);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Проверяем, что все значения y изменены на заданное значение
        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(newValue, function.getY(i), 1e-6, "Y value at index " + i + " is incorrect");
        }

        // Проверяем, что вывод содержит ожидаемые строки
        String output = outputStream.toString();
        for (int i = 0; i < function.getCount(); i++) {
            String expectedOutput = String.format("Writing for index %d complete", i);
            assertTrue(output.contains(expectedOutput), "Output does not contain expected line: " + expectedOutput);
        }
    }

    @Test
    public void testConcurrency() throws InterruptedException {
        double newValue1 = 20.0;
        double newValue2 = 50.0;

        Thread thread1 = new Thread(new WriteTask(newValue1, function));
        Thread thread2 = new Thread(new WriteTask(newValue2, function));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // Проверяем, что значения y изменены одним из двух значений
        for (int i = 0; i < function.getCount(); i++) {
            double yValue = function.getY(i);
            assertTrue(yValue == newValue1 || yValue == newValue2, "Y value at index " + i + " is not one of the expected values");
        }
    }
}
