package concurrent;

import function.api.TabulatedFunction;
import function.ArrayTabulatedFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReadTaskTest {
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
        ReadTask task = new ReadTask(function);
        Thread thread = new Thread(task);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Проверяем, что вывод содержит ожидаемые строки
        String output = outputStream.toString();
        for (int i = 0; i < function.getCount(); i++) {
            String expectedOutput = String.format("After read: i = %d, x = %f, y = %f", i, function.getX(i), function.getY(i));
            assertTrue(output.contains(expectedOutput), "Output does not contain expected line: " + expectedOutput);
        }
    }

    @Test
    public void testConcurrency() throws InterruptedException {
        Thread thread1 = new Thread(new ReadTask(function));
        Thread thread2 = new Thread(new ReadTask(function));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // Проверяем, что оба потока завершили выполнение и вывели правильные данные
        String output = outputStream.toString();
        for (int i = 0; i < function.getCount(); i++) {
            String expectedOutput = String.format("After read: i = %d, x = %f, y = %f", i, function.getX(i), function.getY(i));
            assertTrue(output.contains(expectedOutput), "Output does not contain expected line for concurrency test: " + expectedOutput);
        }
    }
}
