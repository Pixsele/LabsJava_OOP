package function;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AndThenMethodTest {

    @Test
    void testSqrFunctionAndThen() {
        MathFunction sqrFunction = new SqrFunction();
        MathFunction doubleFunction = x -> 2 * x;

        MathFunction compositeFunction = sqrFunction.andThen(doubleFunction);

        assertEquals(2 * Math.pow(2, 2), compositeFunction.apply(2), 1e-9);
        assertEquals(2 * Math.pow(3, 2), compositeFunction.apply(3), 1e-9);
        assertEquals(2 * Math.pow(0, 2), compositeFunction.apply(0), 1e-9);
    }

    @Test
    void testCompositeWithSimpleIterationMethod() {
        MathFunction g = x -> (x + 4) / 2;
        MathFunction simpleIterationMethod = new SimpleIterationMethod(new MathFunction[]{g}, 1e-9, 1000);
        MathFunction sqrFunction = new SqrFunction();
        MathFunction compositeFunction = sqrFunction.andThen(simpleIterationMethod);

        double result = compositeFunction.apply(2);
        assertEquals(4, result, 1e-9);
    }
}

