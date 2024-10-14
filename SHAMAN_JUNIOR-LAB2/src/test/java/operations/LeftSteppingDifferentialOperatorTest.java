package operations;

import function.SqrFunction;
import function.api.MathFunction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LeftSteppingDifferentialOperatorTest {
    @Test
    public void testDerivativeOfSqrFunction() {
        // Квадратичная функция f(x) = x^2
        MathFunction sqrFunction = new SqrFunction();

        // Шаг дифференцирования
        double step = 1e-5;
        LeftSteppingDifferentialOperator operator = new LeftSteppingDifferentialOperator(step);

        // Получаем производную
        MathFunction derivative = operator.derive(sqrFunction);

        // Производная f'(x) = 2x, проверка в точке x = 2
        assertEquals(4.0, derivative.apply(2.0), 1e-3);

        // Проверка в точке x = 1 (f'(x) = 2x = 2)
        assertEquals(2.0, derivative.apply(1.0), 1e-3);

        // Проверка в точке x = 0 (f'(x) = 0)
        assertEquals(0.0, derivative.apply(0.0), 1e-3);
    }

    @Test
    public void testDerivativeOfLinearFunction() {
        // Линейная функция f(x) = 2x + 1
        MathFunction linearFunction = new MathFunction() {
            @Override
            public double apply(double x) {
                return 2 * x + 1;
            }
        };

        // Шаг дифференцирования
        double step = 1e-5;
        LeftSteppingDifferentialOperator operator = new LeftSteppingDifferentialOperator(step);

        // Получаем производную
        MathFunction derivative = operator.derive(linearFunction);

        // Производная f'(x) = 2, проверка в разных точках
        assertEquals(2.0, derivative.apply(2.0), 1e-5);
        assertEquals(2.0, derivative.apply(1.0), 1e-5);
        assertEquals(2.0, derivative.apply(0.0), 1e-5);
    }

    @Test
    public void testUnsupportedApply() {
        LeftSteppingDifferentialOperator operator = new LeftSteppingDifferentialOperator(0.1);

        // Проверяем, что вызов метода apply() выбрасывает UnsupportedOperationException
        assertThrows(UnsupportedOperationException.class, () -> operator.apply(2.0));
    }

    @Test
    public void testInvalidStep() {
        // Проверяем, что при недопустимом значении шага выбрасывается IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new LeftSteppingDifferentialOperator(0.0));
        assertThrows(IllegalArgumentException.class, () -> new LeftSteppingDifferentialOperator(-0.1));
        assertThrows(IllegalArgumentException.class, () -> new LeftSteppingDifferentialOperator(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> new LeftSteppingDifferentialOperator(Double.POSITIVE_INFINITY));
    }

}