package function;

import function.api.MathFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdditionalTest {

    @Test
    void FirstTest() {
        SqrFunction sqr = new SqrFunction();
        MathFunction function1 = (x) -> x + 2;
        MathFunction function2 = (x) -> sqr.apply(x + 1);
        CompositeFunction compositeFunction = new CompositeFunction(function1, function2);

        LinkedListTabulatedFunction tabulatedFunction1 = new LinkedListTabulatedFunction(function1, 0, 3, 4);
        ArrayTabulatedFunction tabulatedFunction2 = new ArrayTabulatedFunction(function2, 0, 3, 4);
        LinkedListTabulatedFunction tabulatedFunction3 = new LinkedListTabulatedFunction(compositeFunction, 0, 3, 4);
        ArrayTabulatedFunction tabulatedFunction4 = new ArrayTabulatedFunction(tabulatedFunction3, 0, 3, 4);
        LinkedListTabulatedFunction tabulatedFunction5 = new LinkedListTabulatedFunction(tabulatedFunction2, 0, 3, 4);

        double[] yArray1 = {2.0, 3.0, 4.0, 5.0};
        double[] yArray2 = {1.0, 4.0, 9.0, 16.0};
        double[] yArray3 = {9.0, 16.0, 25.0, 36.0};
        double[] yArray4 = {9.0, 16.0, 25.0, 36.0};
        double[] yArray5 = {1.0, 4.0, 9.0, 16.0};

        for (int i = 0; i < tabulatedFunction1.getCount(); i++) {
            assertEquals(yArray1[i], tabulatedFunction1.getY(i), 1e-9);
            assertEquals(yArray2[i], tabulatedFunction2.getY(i), 1e-9);
            assertEquals(yArray3[i], tabulatedFunction3.getY(i), 1e-9);
            assertEquals(yArray4[i], tabulatedFunction4.getY(i), 1e-9);
            assertEquals(yArray5[i], tabulatedFunction5.getY(i), 1e-9);
        }
    }

}
