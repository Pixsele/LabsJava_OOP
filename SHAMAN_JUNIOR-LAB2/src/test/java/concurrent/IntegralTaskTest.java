package concurrent;

import function.LinkedListTabulatedFunction;
import function.SqrFunction;
import function.api.MathFunction;
import function.api.TabulatedFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegralTaskTest {

    @Test
    void solveByTrapezoidalRule() {
        MathFunction f = (x)->x*x;
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(f,1,10,10);

        IntegralTask integralTask = new IntegralTask(function,1,10);



    }
}