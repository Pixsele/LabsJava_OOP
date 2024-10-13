package function;
import function.api.MathFunction;

import java.util.Arrays;
public class SimpleIterationMethod implements MathFunction {
    private final MathFunction[] equations;  // Массив функций, представляющих систему уравнений
    private final double epsilon;            // Точность
    private final int maxIterations;         // Максимальное количество итераций

    // Конструктор для инициализации системы уравнений
    public SimpleIterationMethod(MathFunction[] equations, double epsilon, int maxIterations) {
        this.equations = equations;
        this.epsilon = epsilon;
        this.maxIterations = maxIterations;
    }
@Override
    // Метод для выполнения итерационного процесса
    public double apply(double initialGuess) {
        double[] currentGuess = new double[equations.length];  // Текущие значения переменных
        double[] nextGuess = new double[equations.length];     // Следующие значения переменных

        Arrays.fill(currentGuess, initialGuess);

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            boolean converged = true;

            // Вычисляем следующее приближение для каждой переменной
            for (int i = 0; i < equations.length; i++) {
                nextGuess[i] = equations[i].apply(currentGuess[i]);

                // Проверяем сходимость для каждой переменной
                if (Math.abs(nextGuess[i] - currentGuess[i]) >= epsilon) {
                    converged = false;  // Если хоть одно уравнение не сходится, продолжаем итерации
                }
            }

            // Если все уравнения сходятся, возвращаем результат
            if (converged) {
                return nextGuess[0];
            }

            // Обновляем текущее приближение для следующей итерации
            System.arraycopy(nextGuess, 0, currentGuess, 0, currentGuess.length);
        }

        throw new IllegalStateException("Solution not found within maximum iterations");
    }
}


