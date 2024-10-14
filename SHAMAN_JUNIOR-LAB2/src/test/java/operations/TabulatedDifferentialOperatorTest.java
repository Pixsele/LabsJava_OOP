package operations;

import function.ArrayTabulatedFunction;
import function.LinkedListTabulatedFunction;
import function.api.TabulatedFunction;
import function.factory.ArrayTabulatedFunctionFactory;
import function.factory.LinkedListTabulatedFunctionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TabulatedDifferentialOperatorTest {

    @Test
    void getsetTest() {
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator();

        assertInstanceOf(ArrayTabulatedFunctionFactory.class,operator.getFactory());
        operator.setFactory(new LinkedListTabulatedFunctionFactory());
        assertInstanceOf(LinkedListTabulatedFunctionFactory.class,operator.getFactory());
    }

    @Test
    void deriveTestArray() {
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator();
        TabulatedFunction function = operator.derive(new ArrayTabulatedFunction(x -> x*x + 2*x + 1, -3, 3, 5));

        double[] xValues = new double[] {-3, -1.5, 0, 1.5, 3};
        double[] yValues = new double[] {-4, -1, 2, 5, 8};
        double maxEpsilon = function.getY(1) - function.getY(0);

        for (int i = 0; i < function.getCount()-1; i++) {
            if((function.getY(i+1) - function.getY(i))>maxEpsilon){
                maxEpsilon = function.getY(i+1) - function.getY(i);
            }
        }

        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(xValues[i], function.getX(i));
            assertEquals(yValues[i], function.getY(i), maxEpsilon);
        }

    }

    @Test
    void deriveTestLinkedList(){

        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
        TabulatedFunction function = operator.derive(new LinkedListTabulatedFunction(x -> x*x + 2*x + 1, -3, 3, 5));

        double[] xValues = new double[] {-3, -1.5, 0, 1.5, 3};
        double[] yValues = new double[] {-4, -1, 2, 5, 8};
        double maxEpsilon = function.getY(1) - function.getY(0);

        for (int i = 0; i < function.getCount()-1; i++) {
            if((function.getY(i+1) - function.getY(i))>maxEpsilon){
                maxEpsilon = function.getY(i+1) - function.getY(i);
            }
        }

        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(xValues[i], function.getX(i));
            assertEquals(yValues[i], function.getY(i), maxEpsilon);
        }
    }

    @Test
    void applyTest() {
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator();

        assertThrows(UnsupportedOperationException.class,()->operator.apply(1));
    }
}