package concurrent;

import function.api.TabulatedFunction;

public class MultiplyingTask implements Runnable {
    private final TabulatedFunction function;

    // Конструктор, принимающий табулированную функцию
    public MultiplyingTask(TabulatedFunction function) {
        this.function = function;
    }

    @Override
    public void run() {
        // Увеличиваем значение y в два раза для каждой точки
        for (int i = 0; i < function.getCount(); i++) {
            synchronized (function) {
                double currentY = function.getY(i);
                function.setY(i, currentY * 2);
            }
        }
        System.out.println("Thread " + Thread.currentThread().getName() + " completed the task");
    }
}
