package function.factory;

import function.LinkedListTabulatedFunction;
import function.SqrFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTabulatedFunctionFactoryTest {

    @Test
    void createtTest() {
        LinkedListTabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();

        double[] xValues = {1,2,3,4};
        double[] yValues = {1,2,3,4};

        assertInstanceOf(LinkedListTabulatedFunction.class,factory.create(xValues,yValues));
        factory.create(new SqrFunction(),1,10,10);

    }
}