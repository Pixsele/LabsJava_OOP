package function;

import function.api.MathFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompositeFunctionTest {

    @Test
    void apply() {
        MathFunction f = (x)->x;
        MathFunction g = (x)->x*x;
        CompositeFunction func = new CompositeFunction(f,g);
        assertEquals(100,func.apply(10));
        SqrFunction s = new SqrFunction();
        CompositeFunction func1 = new CompositeFunction(g,s);
        assertEquals(256,func1.apply(4));
    }
}