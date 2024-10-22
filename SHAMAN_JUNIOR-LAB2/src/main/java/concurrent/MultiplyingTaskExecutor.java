package concurrent;

import function.api.TabulatedFunction;
import function.UnitFunction;
import function.LinkedListTabulatedFunction;

import java.util.ArrayList;
import java.util.List;


public class MultiplyingTaskExecutor {
    public static void main(String[] args)  {
        TabulatedFunction function = new LinkedListTabulatedFunction(
                new UnitFunction(), 1, 1000, 1000); // Создаем табулированную функцию (от 1 до 1000)
        // Создаем список потоков
        List<Thread> threads = new ArrayList<>();
        // Запускаем 10 задач
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new MultiplyingTask(function));
            threads.add(thread);
        }
        // Запускаем все потоки
        for (Thread thread : threads) {
            thread.start();
        }

        while (!threads.isEmpty()) {
            threads.removeIf(thread -> !thread.isAlive());
        }
        // Выводим табулированную функцию после всех операций
        System.out.println(function);
    }
}
