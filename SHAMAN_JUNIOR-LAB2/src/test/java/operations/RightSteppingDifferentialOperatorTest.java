package operations;

import function.SqrFunction;
import function.api.MathFunction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RightSteppingDifferentialOperatorTest {
    @Test
    public void testDerivativeOfSqrFunction() {
        // Квадратичная функция f(x) = x^2
        MathFunction sqrFunction = new SqrFunction();

        // Шаг дифференцирования
        double step = 1e-5;
        RightSteppingDifferentialOperator operator = new RightSteppingDifferentialOperator(step);

        // Получаем производную
        MathFunction derivative = operator.derive(sqrFunction);

        // Производная f'(x) = 2x, проверка в точке x = 2
        assertEquals(4.0, derivative.apply(2.0), 1e-3); // Производная для правого шага

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
        RightSteppingDifferentialOperator operator = new RightSteppingDifferentialOperator(step);

        // Получаем производную
        MathFunction derivative = operator.derive(linearFunction);

        // Производная f'(x) = 2, проверка в разных точках
        assertEquals(2.0, derivative.apply(2.0), 1e-3);
        assertEquals(2.0, derivative.apply(1.0), 1e-3);
        assertEquals(2.0, derivative.apply(0.0), 1e-3);
    }

    @Test
    public void testDerivativeOfConstantFunction() {
        // Константная функция f(x) = 5
        MathFunction constantFunction = new MathFunction() {
            @Override
            public double apply(double x) {
                return 5;
            }
        };

        // Шаг дифференцирования
        double step = 1e-5;
        RightSteppingDifferentialOperator operator = new RightSteppingDifferentialOperator(step);

        // Получаем производную
        MathFunction derivative = operator.derive(constantFunction);

        // Производная f'(x) = 0 для любой точки, так как производная константы равна нулю
        assertEquals(0.0, derivative.apply(2.0), 1e-3);
        assertEquals(0.0, derivative.apply(1.0), 1e-3);
        assertEquals(0.0, derivative.apply(0.0), 1e-3);
    }

    @Test
    public void testUnsupportedApply() {
        RightSteppingDifferentialOperator operator = new RightSteppingDifferentialOperator(1e-5);

        // Проверяем, что вызов метода apply() выбрасывает UnsupportedOperationException
        assertThrows(UnsupportedOperationException.class, () -> operator.apply(2.0));
    }

    @Test
    public void testInvalidStep() {
        // Проверяем, что при недопустимом значении шага выбрасывается IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new RightSteppingDifferentialOperator(0.0));
        assertThrows(IllegalArgumentException.class, () -> new RightSteppingDifferentialOperator(-0.1));
        assertThrows(IllegalArgumentException.class, () -> new RightSteppingDifferentialOperator(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> new RightSteppingDifferentialOperator(Double.POSITIVE_INFINITY));
    }
}