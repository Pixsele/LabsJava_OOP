package function.Chaplygin;

import org.junit.jupiter.api.Test;

import java.util.function.DoubleFunction;

import static org.junit.jupiter.api.Assertions.*;

class IntegralTest {

    @Test
    void solveByTrapezoidalRule() {
        DoubleFunction<Double> f0 = (x) -> x*x;
        DoubleFunction<Double> f1 = (x) -> Math.pow(Math.E,x);
        DoubleFunction<Double> f2 = (x) -> Math.sin(x*x + 2*x);

        Integral integral0 = new Integral(f0);
        Integral integral1 = new Integral(f1);
        Integral integral2 = new Integral(f2);

        assertEquals(2673.72,integral0.solveByTrapezoidalRule(20,2),0.1);
        assertEquals(23831.041,integral1.solveByTrapezoidalRule(10,0),0.1);
        assertEquals(-0.71805,integral2.solveByTrapezoidalRule(Math.PI,-Math.PI),0.1);


    }
}