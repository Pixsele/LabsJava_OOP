package web.core;

import function.ConstantFunction;
import function.IdentityFunction;
import function.SqrFunction;
import function.api.MathFunction;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MathFunctionProviderTest {
    @Test
    public void testMathFunctions() {
        // Получаем результат вызова метода
        Map<String, MathFunction> result = MathFunctionProvider.mathFunctions();

        // Проверяем, что карта не пустая
        assertNotNull(result);

        // Проверяем наличие всех ключей и соответствующих значений
        assertTrue(result.containsKey("Квадратичная функция"));
        assertTrue(result.containsKey("Константная функция"));
        assertTrue(result.containsKey("Тождественная функция"));

        // Проверяем, что значениями являются экземпляры нужных классов
        assertTrue(result.get("Квадратичная функция") instanceof SqrFunction);
        assertTrue(result.get("Константная функция") instanceof ConstantFunction);
        assertTrue(result.get("Тождественная функция") instanceof IdentityFunction);

        // Дополнительно, можно проверять правильность значений, если они имеют какие-то методы.
        MathFunction sqrFunction = result.get("Квадратичная функция");
        assertNotNull(sqrFunction);
        // Если у SqrFunction есть метод, например, calculate, можно проверить его работу
        // assertEquals(expectedResult, sqrFunction.calculate(input));

        MathFunction constantFunction = result.get("Константная функция");
        assertNotNull(constantFunction);
        // Похожие проверки для других функций...
    }
}