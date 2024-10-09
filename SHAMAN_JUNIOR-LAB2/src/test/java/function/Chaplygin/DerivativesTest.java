package function.Chaplygin;

import org.junit.jupiter.api.Test;

import java.util.function.DoubleFunction;

import static org.junit.jupiter.api.Assertions.*;

class DerivativesTest {

    @Test
    void derive() {
        DoubleFunction<Double> f0 = (x)->x*x;
        DoubleFunction<Double> f1 = (x)->3*x + Math.sin(x);
        DoubleFunction<Double> f2 = (x)->Math.pow(Math.E,x);

        assertEquals(20,Derivatives.derive(f0).apply(10),0.0001);
        assertEquals(2.16093,Derivatives.derive(f1).apply(10),0.0001);
        assertEquals(22026.4679,Derivatives.derive(f2).apply(10),0.0001);
    }
}