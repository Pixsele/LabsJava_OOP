package operations;

import org.junit.jupiter.api.Test;

import function.ArrayTabulatedFunction;
import function.Point;
import function.LinkedListTabulatedFunction;
import function.factory.*;
import function.api.TabulatedFunction;
import org.junit.jupiter.api.Test;


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

}