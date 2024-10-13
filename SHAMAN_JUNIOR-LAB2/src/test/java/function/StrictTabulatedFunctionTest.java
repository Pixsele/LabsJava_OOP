package function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrictTabulatedFunctionTest {

    @Test
    void testConstructor(){
        double[] xValues = {1,2,3,4,5};
        double[] yValues = {2,4,6,8,10};

        StrictTabulatedFunction strFunc1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(xValues,yValues));
        StrictTabulatedFunction strFunc2 = new StrictTabulatedFunction(new ArrayTabulatedFunction(xValues,yValues));

        for(int i = 0;i < strFunc1.getCount();i++){
            assertEquals(xValues[i],strFunc1.getX(i));
            assertEquals(yValues[i],strFunc1.getY(i));
            assertEquals(xValues[i],strFunc2.getX(i));
            assertEquals(yValues[i],strFunc2.getY(i));
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
    void setup(){
        double[] xValues = {1,2,3,4,5};
        double[] yValues = {2,4,6,8,10};

        strFunc1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(xValues,yValues));
        strFunc2 = new StrictTabulatedFunction(new ArrayTabulatedFunction(xValues,yValues));
    }

    @Test
    void getCount() {
        assertEquals(5,strFunc1.getCount());
    }


    @Test
    void setY() {
        strFunc1.setY(2,2.0);
        strFunc2.setY(3,3.5);

        assertEquals(2,strFunc1.getY(2));
        assertEquals(3.5,strFunc2.getY(3));
    }


    @Test
    void testBounds() {
        assertEquals(1,strFunc1.leftBound());
        assertEquals(5,strFunc1.rightBound());

        assertEquals(1,strFunc2.leftBound());
        assertEquals(5,strFunc2.rightBound());
    }


    @Test
    void apply() {
    }
}