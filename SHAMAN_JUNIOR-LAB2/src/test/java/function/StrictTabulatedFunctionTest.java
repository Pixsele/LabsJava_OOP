package function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class StrictTabulatedFunctionTest {

    @Test
    void testConstructor() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {2, 4, 6, 8, 10};

        StrictTabulatedFunction strFunc1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(xValues, yValues));
        StrictTabulatedFunction strFunc2 = new StrictTabulatedFunction(new ArrayTabulatedFunction(xValues, yValues));

        for (int i = 0; i < strFunc1.getCount(); i++) {
            assertEquals(xValues[i], strFunc1.getX(i));
            assertEquals(yValues[i], strFunc1.getY(i));
            assertEquals(xValues[i], strFunc2.getX(i));
            assertEquals(yValues[i], strFunc2.getY(i));
        }
    }

    @Test
    void indexOfXY() {
        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        StrictTabulatedFunction strictTabulatedFunction1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        StrictTabulatedFunction strictTabulatedFunction2 = new StrictTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        assertEquals(0, strictTabulatedFunction1.indexOfX(1.0));
        assertEquals(2, strictTabulatedFunction1.indexOfY(2.0));
        assertEquals(-1, strictTabulatedFunction1.indexOfX(0.0));

        assertEquals(0, strictTabulatedFunction2.indexOfX(1.0));
        assertEquals(2, strictTabulatedFunction2.indexOfY(2.0));
        assertEquals(-1, strictTabulatedFunction2.indexOfX(0.0));
    }

    StrictTabulatedFunction strFunc1;
    StrictTabulatedFunction strFunc2;

    @BeforeEach
    void setup() {
        double[] xValues = {1, 2, 3, 4, 5};
        double[] yValues = {2, 4, 6, 8, 10};

        strFunc1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(xValues, yValues));
        strFunc2 = new StrictTabulatedFunction(new ArrayTabulatedFunction(xValues, yValues));
    }

    @Test
    void getCount() {
        assertEquals(5, strFunc1.getCount());
    }


    @Test
    void setY() {
        strFunc1.setY(2, 2.0);
        strFunc2.setY(3, 3.5);

        assertEquals(2, strFunc1.getY(2));
        assertEquals(3.5, strFunc2.getY(3));
    }


    @Test
    void testBounds() {
        assertEquals(1, strFunc1.leftBound());
        assertEquals(5, strFunc1.rightBound());

        assertEquals(1, strFunc2.leftBound());
        assertEquals(5, strFunc2.rightBound());
    }


    @Test
    void applyTest() {

        assertEquals(2, strFunc1.apply(1));
        assertEquals(2, strFunc2.apply(1));

        assertThrows(UnsupportedOperationException.class, () -> strFunc1.apply(-1));
        assertThrows(UnsupportedOperationException.class, () -> strFunc2.apply(-1));
    }

    @Test
    void testIteratorWithWhile() {
        double[] xArray = {1, 2, 4, 10};
        double[] yArray = {2, 4, 9, 20};
        StrictTabulatedFunction strictTabulatedFunction1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        StrictTabulatedFunction strictTabulatedFunction2 = new StrictTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        Iterator<Point> iterator1 = strictTabulatedFunction1.iterator();
        Iterator<Point> iterator2 = strictTabulatedFunction2.iterator();
        int index = 0;

        while (iterator1.hasNext() && iterator2.hasNext()) {
            Point point1 = iterator1.next();
            Point point2 = iterator2.next();
            assertEquals(strictTabulatedFunction1.getX(index), point1.x, 1e-9);
            assertEquals(strictTabulatedFunction1.getY(index), point1.y, 1e-9);
            assertEquals(strictTabulatedFunction2.getX(index), point2.x, 1e-9);
            assertEquals(strictTabulatedFunction2.getY(index), point2.y, 1e-9);
            index++;
        }

        assertEquals(strictTabulatedFunction1.getCount(), index);
        assertEquals(strictTabulatedFunction2.getCount(), index);
    }

    @Test
    void testIteratorWithFor() {
        double[] xArray = {1, 2, 4, 10};
        double[] yArray = {2, 4.0, 9, 20};
        StrictTabulatedFunction strictTabulatedFunction1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        StrictTabulatedFunction strictTabulatedFunction2 = new StrictTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        int index1 = 0;
        int index2 = 0;

        for (Point point1 : strictTabulatedFunction1) {
            assertEquals(strictTabulatedFunction1.getX(index1), point1.x, 1e-9);
            assertEquals(strictTabulatedFunction1.getY(index1), point1.y, 1e-9);
            ++index1;
        }

        for (Point point2 : strictTabulatedFunction2) {
            assertEquals(strictTabulatedFunction2.getX(index2), point2.x, 1e-9);
            assertEquals(strictTabulatedFunction2.getY(index2), point2.y, 1e-9);
            ++index2;
        }

        assertEquals(strictTabulatedFunction1.getCount(), index1);
        assertEquals(strictTabulatedFunction2.getCount(), index2);
    }
}