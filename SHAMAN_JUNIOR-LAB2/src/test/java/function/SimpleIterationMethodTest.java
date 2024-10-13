package function;

import function.api.MathFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class SimpleIterationMethodTest {

    @Test
    public void aSingleEquationTest() {
        // Уравнение x = (5-x)/3, точное решение: x = 1.25
        MathFunction equation = x -> (5-x)/3;

        MathFunction[] equations = new MathFunction[]{equation};
        SimpleIterationMethod iterationMethod = new SimpleIterationMethod(equations, 1e-9, 100);

        double initialGuess = 0;
        double res = iterationMethod.apply(initialGuess);

        assertEquals(1.25, res, 1e-6);
    }

    @Test
    public void testSystemOfEquations() {
        // Система уравнений:
        // x1 = (x2 + 2) / 3
        // x2 = (x1 + 1) / 2
        MathFunction equation1 = x -> (x + 2) / 3;
        MathFunction equation2 = x -> (x + 1) / 2;

        MathFunction[] equations = new MathFunction[]{equation1, equation2};
        SimpleIterationMethod iterationMethod = new SimpleIterationMethod(equations, 1e-9, 100);

        double initialGuess = 0;
        double root = iterationMethod.apply(initialGuess);

        assertTrue(Math.abs(root - 1.0) < 1e-9 || Math.abs(root - 1.5) < 1e-9);
    }

    @Test
    public void testNotCovergence(){
        //ур-ние, которое не сходится
        MathFunction equation = x -> 4 * x; // x=4x не имеет корней
        MathFunction[] equations = new MathFunction[]{equation};
        SimpleIterationMethod iterationMethod = new SimpleIterationMethod(equations, 1e-9, 100);

        assertThrows(IllegalStateException.class, () -> iterationMethod.apply((1.0)));
    }

    @Test
    public void testResultKnown(){
        //yp-ние, результат которого известен
        MathFunction equation = x -> x;
        MathFunction[] equations = new MathFunction[]{equation};
        SimpleIterationMethod iterationMethod = new SimpleIterationMethod(equations, 1e-9, 100);

        double initialGuess = 1.0;
        double root = iterationMethod.apply(initialGuess);

        assertEquals(1.0, root, 1e-9);
    }
}