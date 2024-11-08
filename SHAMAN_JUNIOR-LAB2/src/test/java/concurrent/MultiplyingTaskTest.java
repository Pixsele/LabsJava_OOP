package concurrent;

import function.api.TabulatedFunction;
import function.ArrayTabulatedFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiplyingTaskTest {
    private TabulatedFunction function;

    @BeforeEach
    public void setUp() {
        // Создаем табулированную функцию для теста
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {2, 4, 6, 8, 10};
        function = new ArrayTabulatedFunction(xValues, yValues);
    }

    @Test
    public void testRun() {
        MultiplyingTask task = new MultiplyingTask(function);
        Thread thread = new Thread(task);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Проверяем, что все значения y увеличены в два раза
        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(function.getY(i), 2 * (i + 1) * 2, 1e-6);
        }
    }

    @Test
    public void testConcurrency() throws InterruptedException {
        Thread thread1 = new Thread(new MultiplyingTask(function));
        Thread thread2 = new Thread(new MultiplyingTask(function));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // Проверяем, что все значения y корректно увеличены (по итогу 4x)
        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(function.getY(i), 4 * (i + 1) * 2, 1e-6);
        }
    }
}
