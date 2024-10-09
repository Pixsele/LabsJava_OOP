package function;

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

   /* @Test
    public void TestingASistemOfEquations() {
        // Уравнения
        //x1 = (5*x2+3)/2
        //x2 =(x1-1)/3
        MathFunction equation1 = x -> (x + 3) / 2;
        MathFunction equation2 = x -> (x - 1) / 3;

        MathFunction[] equations = new MathFunction[]{equation1,equation2};
        SimpleIterationMethod iterationMethod = new SimpleIterationMethod(equations, 1e-9, 100);

        double initialGuess = 0;
        double res = iterationMethod.apply(initialGuess);

        assertTrue(Math.abs(res - 1.6) < 1e-9 || Math.abs(res - 0.2) < 1e-9);
    }*/
}