package function;

import function.api.MathFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathFunctionTest {

    // Тестирование композиции функций
    @Test
    public void testAndThenWithSimpleFunctions() {
        // Функция умножения на 2
        MathFunction multiplyByTwo = x -> x * 2;
        // Функция прибавления 3
        MathFunction addThree = x -> x + 3;
        // Композиция: (x * 2) -> (x * 2 + 3)
        MathFunction compositeFunction = multiplyByTwo.andThen(addThree);

        assertEquals(11, compositeFunction.apply(4), 1e-9);  // (4 * 2) + 3 = 11
        assertEquals(5, compositeFunction.apply(1), 1e-9);   // (1 * 2) + 3 = 5
        assertEquals(19, compositeFunction.apply(8), 1e-9);  // (8 * 2) + 3 = 19
    }

    // Тестирование с идентичной функцией
    @Test
    public void testAndThenWithIdentityFunction() {
        // Идентичная функция: x -> x
        MathFunction identity = x -> x;
        // Функция возведения в квадрат
        MathFunction square = x -> x * x;
        // Композиция: x -> x -> x * x
        MathFunction compositeFunction = identity.andThen(square);

        assertEquals(16, compositeFunction.apply(4), 1e-9);  // 4 * 4 = 16
        assertEquals(1, compositeFunction.apply(1), 1e-9);   // 1 * 1 = 1
        assertEquals(64, compositeFunction.apply(8), 1e-9);  // 8 * 8 = 64
    }

    // Тестирование композиции трёх функций
    @Test
    public void testAndThenWithThreeFunctions() {
        // Функция: x -> x + 2
        MathFunction addTwo = x -> x + 2;
        // Функция: x -> x * 3
        MathFunction multiplyByThree = x -> x * 3;
        // Функция: x -> x - 1
        MathFunction subtractOne = x -> x - 1;
        // Композиция: ((x + 2) * 3) - 1
        MathFunction compositeFunction = addTwo.andThen(multiplyByThree).andThen(subtractOne);

        assertEquals(14, compositeFunction.apply(3), 1e-9);  // ((3 + 2) * 3) - 1 = 14
        assertEquals(29, compositeFunction.apply(8), 1e-9);  // ((8 + 2) * 3) - 1 = 29
        assertEquals(5, compositeFunction.apply(0), 1e-9);   // ((0 + 2) * 3) - 1 = 5
    }
}