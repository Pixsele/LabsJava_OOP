package concurrent;

import function.api.TabulatedFunction;
import function.*;
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
    }
}