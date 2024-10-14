package operations;

import function.SqrFunction;
import function.api.MathFunction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MiddleSteppingDifferentialOperatorTest {
    @Test
    public void testDerivativeOfSqrFunction() {
        // Квадратичная функция f(x) = x^2
        MathFunction sqrFunction = new SqrFunction();

        // Шаг дифференцирования
        double step = 1e-5;
        MiddleSteppingDifferentialOperator operator = new MiddleSteppingDifferentialOperator(step);

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
        // Линейная функция f(x) = 3x + 2
        MathFunction linearFunction = new MathFunction() {
            @Override
            public double apply(double x) {
                return 3 * x + 2;
            }
        };

        // Шаг дифференцирования
        double step = 1e-5;
        MiddleSteppingDifferentialOperator operator = new MiddleSteppingDifferentialOperator(step);

        // Получаем производную
        MathFunction derivative = operator.derive(linearFunction);

        // Производная f'(x) = 3, проверка в разных точках
        assertEquals(3.0, derivative.apply(2.0), 1e-3);
        assertEquals(3.0, derivative.apply(1.0), 1e-3);
        assertEquals(3.0, derivative.apply(0.0), 1e-3);
    }

    @Test
    public void testDerivativeOfConstantFunction() {
        // Константная функция f(x) = 7
        MathFunction constantFunction = new MathFunction() {
            @Override
            public double apply(double x) {
                return 7;
            }
        };

        // Шаг дифференцирования
        double step = 1e-5;
        MiddleSteppingDifferentialOperator operator = new MiddleSteppingDifferentialOperator(step);

        // Получаем производную
        MathFunction derivative = operator.derive(constantFunction);

        // Производная f'(x) = 0 для любой точки, так как производная константы равна нулю
        assertEquals(0.0, derivative.apply(2.0), 1e-3);
        assertEquals(0.0, derivative.apply(1.0), 1e-3);
        assertEquals(0.0, derivative.apply(0.0), 1e-3);
    }

    @Test
    public void testUnsupportedApply() {
        MiddleSteppingDifferentialOperator operator = new MiddleSteppingDifferentialOperator(0.1);

        // Проверяем, что вызов метода apply() выбрасывает UnsupportedOperationException
        assertThrows(UnsupportedOperationException.class, () -> operator.apply(2.0));
    }

    @Test
    public void testInvalidStep() {
        // Проверяем, что при недопустимом значении шага выбрасывается IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new MiddleSteppingDifferentialOperator(0.0));
        assertThrows(IllegalArgumentException.class, () -> new MiddleSteppingDifferentialOperator(-0.1));
        assertThrows(IllegalArgumentException.class, () -> new MiddleSteppingDifferentialOperator(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> new MiddleSteppingDifferentialOperator(Double.POSITIVE_INFINITY));
    }
}