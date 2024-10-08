package function;

import org.junit.jupiter.api.Test;

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
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        function.remove(2);

        assertEquals(2, function.getCount());

        assertEquals(1.0, function.getX(0));
        assertEquals(2.0, function.getX(1));

        assertEquals(1.0, function.getY(0));
        assertEquals(4.0, function.getY(1));

        function.remove(0);

        assertEquals(1, function.getCount());

        assertEquals(2.0, function.getX(0));

        assertEquals(4.0, function.getY(0));
    }


    @Test
    void floorIndexOfX() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(2,function.floorIndexOfX(15));
    }
}
