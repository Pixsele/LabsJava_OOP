package concurrent;

import function.api.TabulatedFunction;
import function.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class SynchronizedTabulatedFunctionTest {

    @Test
    public void testDoSynchronouslyWithReturnValue() {
        // Создаем табулированную функцию
        TabulatedFunction function = new LinkedListTabulatedFunction(new UnitFunction(), 1, 10, 10);
        SynchronizedTabulatedFunction synchronizedFunction = new SynchronizedTabulatedFunction(function);

        // Операция получения значения y по индексу
        double yValue = synchronizedFunction.doSynchronously(f -> f.getY(5));

        assertEquals(1.0, yValue, 0.001);
    }

    @Test
    public void testDoSynchronouslyWithoutReturnValue() {
        TabulatedFunction function = new LinkedListTabulatedFunction(new UnitFunction(), 1, 10, 10);
        SynchronizedTabulatedFunction synchronizedFunction = new SynchronizedTabulatedFunction(function);

        // Операция изменения значения y по индексу (без возвращаемого значения)
        synchronizedFunction.doSynchronously(f -> {
            f.setY(5, 2.0);
            return null; // Возвращаем null для Void
        });

        assertEquals(2.0, synchronizedFunction.doSynchronously(f -> f.getY(5)), 0.001);

        synchronizedFunction.insert(1,2);
        synchronizedFunction.getXValues();
        synchronizedFunction.getYValues();
        synchronizedFunction.remove(1);
    }

    private TabulatedFunction function;
    private SynchronizedTabulatedFunction synchronizedFunction;

    @BeforeEach
    void setup(){
        function = new LinkedListTabulatedFunction(new UnitFunction(), 1, 10, 10);
        synchronizedFunction = new SynchronizedTabulatedFunction(function);
    }

    @Test
    public void testMethods(){
        assertEquals(10,synchronizedFunction.getCount());
        assertEquals(1,synchronizedFunction.getX(0));
        assertEquals(9,synchronizedFunction.indexOfX(10));
        assertEquals(-1,synchronizedFunction.indexOfY(12));
        assertEquals(1,synchronizedFunction.leftBound());
        assertEquals(10,synchronizedFunction.rightBound());
        assertEquals(1.0,synchronizedFunction.apply(1));

        Iterator<Point> iter = synchronizedFunction.iterator();
        assertEquals(1,iter.next().x);
    }

}