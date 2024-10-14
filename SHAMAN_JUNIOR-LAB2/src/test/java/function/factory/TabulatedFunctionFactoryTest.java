package function.factory;

import function.StrictTabulatedFunction;
import function.UnmodifiableTabulatedFunction;
import function.api.TabulatedFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TabulatedFunctionFactoryTest {

    @Test
    void createStrictTest() {
        TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionFactory factory1 = new LinkedListTabulatedFunctionFactory();

        double[] xV = {1,2,3};
        double[] yV = {2,4,6};

        TabulatedFunction StrFunc = factory.createStrict(xV,yV);
        TabulatedFunction StrFunc1 = factory1.createStrict(xV,yV);

        assertInstanceOf(StrictTabulatedFunction.class,StrFunc);
        assertInstanceOf(StrictTabulatedFunction.class,StrFunc1);

        assertThrows(UnsupportedOperationException.class, ()->StrFunc.apply(-1));
        assertThrows(UnsupportedOperationException.class, ()->StrFunc1.apply(-1));
    }

    @Test
    void createStrictUnmodifiableTest() {
        TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionFactory factory1 = new LinkedListTabulatedFunctionFactory();

        double[] xV = {1,2,3};
        double[] yV = {2,4,6};

        TabulatedFunction StrFunc = factory.createStrictUnmodifiable(xV,yV);
        TabulatedFunction StrFunc1 = factory1.createStrictUnmodifiable(xV,yV);

        assertInstanceOf(StrictTabulatedFunction.class,StrFunc);
        assertInstanceOf(StrictTabulatedFunction.class,StrFunc1);

        assertThrows(UnsupportedOperationException.class, () -> StrFunc.setY(0, 10));
        assertThrows(UnsupportedOperationException.class, () -> StrFunc1.setY(0, 10));

        assertThrows(UnsupportedOperationException.class, ()->StrFunc.apply(-1));
        assertThrows(UnsupportedOperationException.class, ()->StrFunc1.apply(-1));
    }

    @Test
    void createUnmodifiableTest() {
        TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionFactory factory1 = new LinkedListTabulatedFunctionFactory();

        double[] xV = {1,2,3};
        double[] yV = {2,4,6};

        TabulatedFunction StrFunc = factory.createUnmodifiable(xV,yV);
        TabulatedFunction StrFunc1 = factory1.createUnmodifiable(xV,yV);

        assertInstanceOf(UnmodifiableTabulatedFunction.class,StrFunc);
        assertInstanceOf(UnmodifiableTabulatedFunction.class,StrFunc1);

        assertThrows(UnsupportedOperationException.class, () -> StrFunc.setY(0, 10));
        assertThrows(UnsupportedOperationException.class, () -> StrFunc1.setY(0, 10));
    }
}