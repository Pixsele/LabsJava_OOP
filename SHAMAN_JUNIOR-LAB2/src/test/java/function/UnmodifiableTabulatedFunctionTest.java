package function;

import org.junit.jupiter.api.Test;
import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class UnmodifiableTabulatedFunctionTest {

    @Test
    void testConstructorWithArrays(){

        double[] xArray = {2.0, 2.5, 4.8, 10.0};
        double[] yArray = {0.5, 3.0, 7.0, 1.1};
        UnmodifiableTabulatedFunction unmodifiableTabulatedFunction1 = new UnmodifiableTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        UnmodifiableTabulatedFunction unmodifiableTabulatedFunction2 = new UnmodifiableTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        assertEquals(4, unmodifiableTabulatedFunction1.getCount());
        assertEquals(4, unmodifiableTabulatedFunction2.getCount());

        for (int i = 0; i < unmodifiableTabulatedFunction1.getCount(); i++) {
            assertEquals(xArray[i], unmodifiableTabulatedFunction1.getX(i), 1e-9);
            assertEquals(yArray[i], unmodifiableTabulatedFunction1.getY(i), 1e-9);
            assertEquals(xArray[i], unmodifiableTabulatedFunction2.getX(i), 1e-9);
            assertEquals(yArray[i], unmodifiableTabulatedFunction2.getY(i), 1e-9);
        }
    }

    @Test
    void testConstructorArraysWithDifferentSizeThrowsException(){

        double[] xArray = {1.0, 5.0, 6.5};
        double[] yArray = {1.0, 3.0, 7.0, 10.1};

        assertThrows(DifferentLengthOfArraysException.class, () -> new UnmodifiableTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray)));
        assertThrows(DifferentLengthOfArraysException.class, () -> new UnmodifiableTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray)));
    }

    @Test
    public void testConstructorAndGetters() {
        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {1.0, 4.0, 9.0, 16.0};
        UnmodifiableTabulatedFunction unmodifiableFunction = new UnmodifiableTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        assertEquals(4, unmodifiableFunction.getCount());
        for (int i = 0; i < unmodifiableFunction.getCount(); i++) {
            assertEquals(xArray[i], unmodifiableFunction.getX(i), 1e-9);
            assertEquals(yArray[i], unmodifiableFunction.getY(i), 1e-9);
        }
    }

    @Test
    void testGetterWithIndexLesserThanZero(){

        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        UnmodifiableTabulatedFunction unmodifiableTabulatedFunction1 = new UnmodifiableTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));

        assertEquals(2.0, unmodifiableTabulatedFunction1.getX(-3));
        assertEquals(1.1, unmodifiableTabulatedFunction1.getY(-1));
    }


    @Test
    public void testApplyMethodOnArrayTabulatedFunction() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        UnmodifiableTabulatedFunction unmodifiableFunction = new UnmodifiableTabulatedFunction(arrayFunction);

        // Проверка работы метода apply()
        assertEquals(4.0, unmodifiableFunction.apply(1.0), 0.0001);
        assertEquals(5.0, unmodifiableFunction.apply(2.0), 0.0001);
        assertEquals(6.0, unmodifiableFunction.apply(3.0), 0.0001);
    }

    @Test
    public void testApplyMethodOnLinkedListTabulatedFunction() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};
        LinkedListTabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(xValues, yValues);
        UnmodifiableTabulatedFunction unmodifiableFunction = new UnmodifiableTabulatedFunction(linkedListFunction);

        // Проверка работы метода apply()
        assertEquals(4.0, unmodifiableFunction.apply(1.0), 0.0001);
        assertEquals(5.0, unmodifiableFunction.apply(2.0), 0.0001);
        assertEquals(6.0, unmodifiableFunction.apply(3.0), 0.0001);
    }

    @Test
    public void testSetYThrowsExceptionOnArrayTabulatedFunction() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        UnmodifiableTabulatedFunction unmodifiableFunction = new UnmodifiableTabulatedFunction(arrayFunction);

        // Проверка, что setY выбрасывает UnsupportedOperationException
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableFunction.setY(0, 10.0));
    }

    @Test
    public void testSetYThrowsExceptionOnLinkedListTabulatedFunction() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};
        LinkedListTabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(xValues, yValues);
        UnmodifiableTabulatedFunction unmodifiableFunction = new UnmodifiableTabulatedFunction(linkedListFunction);

        // Проверка, что setY выбрасывает UnsupportedOperationException
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableFunction.setY(0, 10.0));
    }

    @Test
    public void testIndexOfX() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {2.0, 4.0, 6.0};
        ArrayTabulatedFunction function1 = new ArrayTabulatedFunction(xValues, yValues);
        UnmodifiableTabulatedFunction function = new UnmodifiableTabulatedFunction(function1);


        assertEquals(1, function.indexOfX(2.0));
        assertEquals(-1, function.indexOfX(4.0));
    }

    @Test
    public void testIndexOfY() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {2.0, 4.0, 6.0};
        ArrayTabulatedFunction function1 = new ArrayTabulatedFunction(xValues, yValues);
        UnmodifiableTabulatedFunction function = new UnmodifiableTabulatedFunction(function1);
        assertEquals(1, function.indexOfY(4.0));
        assertEquals(-1, function.indexOfY(5.0));
    }

    @Test
    public void testLeftBound() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {2.0, 4.0, 6.0};
        ArrayTabulatedFunction function1 = new ArrayTabulatedFunction(xValues, yValues);
        UnmodifiableTabulatedFunction function = new UnmodifiableTabulatedFunction(function1);
        assertEquals(1.0, function.leftBound());
    }

    @Test
    public void testRightBound() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {2.0, 4.0, 6.0};
        ArrayTabulatedFunction function1 = new ArrayTabulatedFunction(xValues, yValues);
        UnmodifiableTabulatedFunction function = new UnmodifiableTabulatedFunction(function1);
        assertEquals(3.0, function.rightBound());
    }

    @Test
    public void testArrayIteratorWithWhile() {
        ArrayTabulatedFunction function1 = new ArrayTabulatedFunction(new double[]{1.0, 2.0, 3.0}, new double[]{10.0, 20.0, 30.0});
        UnmodifiableTabulatedFunction function = new UnmodifiableTabulatedFunction(function1);

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
    void testRemoveInsert() {
        double[] xArray = {1, 2, 4, 10};
        double[] yArray = {2, 4.0, 9, 20};
        UnmodifiableTabulatedFunction unmodifiableTabulatedFunction= new UnmodifiableTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));

        assertArrayEquals(xArray,unmodifiableTabulatedFunction.getXValues());
        assertArrayEquals(yArray,unmodifiableTabulatedFunction.getYValues());

        unmodifiableTabulatedFunction.insert(1,2);
        unmodifiableTabulatedFunction.remove(2);
    }
}
