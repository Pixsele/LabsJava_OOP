package function;

import exceptions.RemoveIncorrectPoint;
import function.api.MathFunction;
import org.junit.jupiter.api.Test;
import java.util.Iterator;

import exceptions.DifferentLengthOfArraysException;
import exceptions.InterpolationException;
import exceptions.ArrayIsNotSortedException;

import static org.junit.jupiter.api.Assertions.*;

class ArrayTabulatedFunctionTest {

    @Test
    public void testConstructorWithArrays() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {2.0, 4.0, 6.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(3, function.getCount());
        assertArrayEquals(xValues, new double[]{function.getX(0), function.getX(1), function.getX(2)});
        assertArrayEquals(yValues, new double[]{function.getY(0), function.getY(1), function.getY(2)});
    }

    @Test
    public void testConstructorWithMathFunction() {
        MathFunction linearFunction = x -> 2 * x;
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(linearFunction, 0.0, 4.0, 5);

        assertEquals(5, function.getCount());
        assertArrayEquals(new double[]{0.0, 1.0, 2.0, 3.0, 4.0},
                new double[]{function.getX(0), function.getX(1), function.getX(2), function.getX(3), function.getX(4)});
        assertArrayEquals(new double[]{0.0, 2.0, 4.0, 6.0, 8.0},
                new double[]{function.getY(0), function.getY(1), function.getY(2), function.getY(3), function.getY(4)});
    }

    @Test
    public void testGetSetY() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {2.0, 4.0, 6.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(4.0, function.getY(1));
        function.setY(1, 5.0);
        assertEquals(5.0, function.getY(1));
    }

    @Test
    public void testIndexOfX() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {2.0, 4.0, 6.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(1, function.indexOfX(2.0));
        assertEquals(-1, function.indexOfX(4.0));
    }

    @Test
    public void testIndexOfY() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {2.0, 4.0, 6.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(1, function.indexOfY(4.0));
        assertEquals(-1, function.indexOfY(5.0));
    }

    @Test
    public void testLeftBound() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {2.0, 4.0, 6.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(1.0, function.leftBound());
    }

    @Test
    public void testRightBound() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {2.0, 4.0, 6.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(3.0, function.rightBound());
    }

    @Test
    public void testInsert() {
        double[] xValues = {1.0, 3.0, 5.0};
        double[] yValues = {2.0, 6.0, 10.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        // Вставка нового элемента между существующими x
        function.insert(2.0, 4.0);
        assertEquals(4, function.getCount());
        assertEquals(2.0, function.getX(1));
        assertEquals(4.0, function.getY(1));

        // Вставка в начало
        function.insert(0.0, 0.0);
        assertEquals(5, function.getCount());
        assertEquals(0.0, function.getX(0));
        assertEquals(0.0, function.getY(0));

        // Вставка в конец
        function.insert(6.0, 12.0);
        assertEquals(6, function.getCount());
        assertEquals(6.0, function.getX(5));
        assertEquals(12.0, function.getY(5));
    }

    @Test
    public void testInterpolate() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {2.0, 4.0, 6.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(3.0, function.interpolate(1.5, 0), 1e-9);
        assertEquals(5.0, function.interpolate(2.5, 1), 1e-9);
    }

    @Test
    public void testExtrapolateLeft() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {2.0, 4.0, 6.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(1.0, function.extrapolateLeft(0.5), 1e-9);
    }

    @Test
    public void testExtrapolateRight() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {2.0, 4.0, 6.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(7.0, function.extrapolateRight(3.5), 1e-9);
    }

    @Test
    void testRemove() {
        double[] xValues = {1.0, 2.0, 3.0,5.0,6.0};
        double[] yValues = {1.0, 4.0, 9.0,5.0,6.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        function.remove(2);

        assertEquals(4, function.getCount());

        assertEquals(1.0, function.getX(0));
        assertEquals(2.0, function.getX(1));

        assertEquals(1.0, function.getY(0));
        assertEquals(4.0, function.getY(1));

        assertThrows(RemoveIncorrectPoint.class, () -> function.remove(-1));
        assertThrows(RemoveIncorrectPoint.class, () -> function.remove(4));
        function.remove(0);
        function.remove(0);
        assertThrows(IllegalStateException.class, () -> function.remove(0));
    }


    @Test
    void floorIndexOfX() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(2,function.floorIndexOfX(15));
        assertThrows(IllegalArgumentException.class, ()-> function.floorIndexOfX(-1));
    }

    @Test
    public void testInterpolate_ValidInterval() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        // Проверка корректного результата интерполяции
        assertEquals(4.5, function.interpolate(1.5, 0), 0.0001);
    }

    @Test
    public void testInterpolate_xOutsideInterval() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        // x за пределами интервала [xValues[floorIndex], xValues[floorIndex + 1]]
        assertThrows(InterpolationException.class, () -> function.interpolate(2.5, 0));
    }


    @Test
    public void testCheckLengthIsTheSame_DifferentLength() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0};
        assertThrows(DifferentLengthOfArraysException.class, () ->
                new ArrayTabulatedFunction(xValues, yValues)
        );
    }

    @Test
    public void testCheckSorted_UnsortedArray() {
        double[] xValues = {1.0, 3.0, 2.0};
        double[] yValues = {4.0, 5.0, 6.0};
        assertThrows(ArrayIsNotSortedException.class, () ->
                new ArrayTabulatedFunction(xValues, yValues)
        );
    }

    @Test
    public void testIteratorHasNextAndNext() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        Iterator<Point> iterator = function.iterator();

        // Проверяем hasNext() и next() для каждого элемента
        for (int i = 0; i < xValues.length; i++) {
            assertTrue(iterator.hasNext());
            Point point = iterator.next();
            assertEquals(xValues[i], point.x, 0.0001);
            assertEquals(yValues[i], point.y, 0.0001);
        }

        // После последнего элемента hasNext() должно вернуть false
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testArrayIteratorWithWhile() {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(new double[]{1.0, 2.0, 3.0}, new double[]{10.0, 20.0, 30.0});
        Iterator<Point> iterator = function.iterator();
        int index = 0;

        while (iterator.hasNext()) {
            Point point = iterator.next();
            assertEquals(function.getX(index), point.x, 1e-9);
            assertEquals(function.getY(index), point.y, 1e-9);
            index++;
        }

        assertEquals(function.getCount(), index);
    }

    @Test
    public void testForEach() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {10.0, 20.0, 30.0};

        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        int index = 0;
        for (Point point : function) {
            assertEquals(xValues[index], point.x, 1e-9);
            assertEquals(yValues[index], point.y, 1e-9);
            index++;
        }
        assertEquals(xValues.length, index);
    }

    @Test
    public void testConstructTrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new ArrayTabulatedFunction(new SqrFunction(),10, 20,1)
        );
    }
}
