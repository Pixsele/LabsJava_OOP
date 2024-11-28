package function.factory;

import function.ArrayTabulatedFunction;
import function.SqrFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayTabulatedFunctionFactoryTest {

    @Test
    void createTest() {
        ArrayTabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();

        double[] xValues = {1,2,3,4};
        double[] yValues = {1,2,3,4};


        assertInstanceOf(ArrayTabulatedFunction.class,factory.create(xValues,yValues));
        factory.create(new SqrFunction(),1,10,10);
    }
}