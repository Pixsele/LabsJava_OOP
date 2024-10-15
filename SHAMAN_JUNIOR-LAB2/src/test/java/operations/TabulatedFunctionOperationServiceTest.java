package operations;

import org.junit.jupiter.api.Test;

import function.ArrayTabulatedFunction;
import function.Point;
import function.LinkedListTabulatedFunction;
import function.factory.*;
import function.api.TabulatedFunction;
import org.junit.jupiter.api.Test;
import exceptions.InconsistentFunctionsException;

import static org.junit.jupiter.api.Assertions.*;

class TabulatedFunctionOperationServiceTest {

    @Test
    public void testAsPointsWithArrayTabulatedFunction() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);

        Point[] points = TabulatedFunctionOperationService.asPoints(arrayFunction);

        // Проверка длины массива
        assertEquals(3, points.length);

        // Проверка каждой точки
        assertEquals(1.0, points[0].x, 0.0001);
        assertEquals(4.0, points[0].y, 0.0001);

        assertEquals(2.0, points[1].x, 0.0001);
        assertEquals(5.0, points[1].y, 0.0001);

        assertEquals(3.0, points[2].x, 0.0001);
        assertEquals(6.0, points[2].y, 0.0001);
    }

    @Test
    void testAsPointsWithLinkedListTabulatedFunction() {
        double[] xArray = {1.0, 2.0, 3.0};
        double[] yArray = {0.5, 1.0, 4.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xArray, yArray);

        Point[] points = TabulatedFunctionOperationService.asPoints(function);

        assertNotNull(points);
        assertEquals(xArray.length, points.length);
        for (int i = 0; i < points.length; i++) {
            assertNotNull(points[i]);
            assertEquals(xArray[i], points[i].x);
            assertEquals(yArray[i], points[i].y);
        }
    }

    @Test
    public void testAdditionWithSameFunctionTypes() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValuesA = {4.0, 5.0, 6.0};
        double[] yValuesB = {7.0, 8.0, 9.0};

        TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(factory);

        ArrayTabulatedFunction functionA = new ArrayTabulatedFunction(xValues, yValuesA);
        ArrayTabulatedFunction functionB = new ArrayTabulatedFunction(xValues, yValuesB);

        TabulatedFunction result = service.add(functionA, functionB);

        assertEquals(11.0, result.apply(1.0), 0.0001);
        assertEquals(13.0, result.apply(2.0), 0.0001);
        assertEquals(15.0, result.apply(3.0), 0.0001);
    }

    @Test
    public void testSubtractionWithDifferentFunctionTypes() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValuesA = {10.0, 20.0, 30.0};
        double[] yValuesB = {5.0, 15.0, 25.0};

        TabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(factory);

        LinkedListTabulatedFunction functionA = new LinkedListTabulatedFunction(xValues, yValuesA);
        ArrayTabulatedFunction functionB = new ArrayTabulatedFunction(xValues, yValuesB);

        TabulatedFunction result = service.subtract(functionA, functionB);

        assertEquals(5.0, result.apply(1.0), 0.0001);
        assertEquals(5.0, result.apply(2.0), 0.0001);
        assertEquals(5.0, result.apply(3.0), 0.0001);
    }

    @Test
    public void testInconsistentFunctionsExceptionByDifferentLength() {
        double[] xValuesA = {1.0, 2.0, 3.0};
        double[] xValuesB = {1.0, 2.0};
        double[] yValuesA = {4.0, 5.0, 6.0};
        double[] yValuesB = {7.0, 8.0};

        TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(factory);

        ArrayTabulatedFunction functionA = new ArrayTabulatedFunction(xValuesA, yValuesA);
        ArrayTabulatedFunction functionB = new ArrayTabulatedFunction(xValuesB, yValuesB);

        assertThrows(InconsistentFunctionsException.class, () -> service.add(functionA, functionB));
    }

    @Test
    public void testInconsistentFunctionsExceptionByDifferentXValues() {
        double[] xValuesA = {1.0, 2.0, 3.0};
        double[] xValuesB = {1.0, 2.5, 3.0};
        double[] yValuesA = {4.0, 5.0, 6.0};
        double[] yValuesB = {7.0, 8.0, 9.0};

        TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(factory);

        ArrayTabulatedFunction functionA = new ArrayTabulatedFunction(xValuesA, yValuesA);
        ArrayTabulatedFunction functionB = new ArrayTabulatedFunction(xValuesB, yValuesB);

        assertThrows(InconsistentFunctionsException.class, () -> service.add(functionA, functionB));
    }

    @Test
    public void testMultiplyWithSameFunctionTypes() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValuesA = {4.0, 5.0, 6.0};
        double[] yValuesB = {7.0, 8.0, 9.0};

        TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(factory);

        ArrayTabulatedFunction functionA = new ArrayTabulatedFunction(xValues, yValuesA);
        ArrayTabulatedFunction functionB = new ArrayTabulatedFunction(xValues, yValuesB);

        TabulatedFunction result = service.multiply(functionA, functionB);

        assertEquals(28.0, result.apply(1.0), 0.0001);
        assertEquals(40.0, result.apply(2.0), 0.0001);
        assertEquals(54.0, result.apply(3.0), 0.0001);
    }

    @Test
    public void testDevisionWithSameFunctionTypes() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValuesA = {8.0, 15.0, 60.0};
        double[] yValuesB = {2.0, 3.0, 10.0};

        TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(factory);

        ArrayTabulatedFunction functionA = new ArrayTabulatedFunction(xValues, yValuesA);
        ArrayTabulatedFunction functionB = new ArrayTabulatedFunction(xValues, yValuesB);

        TabulatedFunction result = service.devide(functionA, functionB);

        assertEquals(4.0, result.apply(1.0), 0.0001);
        assertEquals(5.0, result.apply(2.0), 0.0001);
        assertEquals(6.0, result.apply(3.0), 0.0001);
    }

    @Test
    public void getSetTest(){
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();

        assertInstanceOf(ArrayTabulatedFunctionFactory.class,service.getFactory());

        service.setFactory(new LinkedListTabulatedFunctionFactory());

        assertInstanceOf(LinkedListTabulatedFunctionFactory.class,service.getFactory());
    }
}