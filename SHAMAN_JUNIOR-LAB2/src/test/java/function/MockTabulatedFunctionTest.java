package function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class MockTabulatedFunctionTest {

    private MockTabulatedFunction func;

    @BeforeEach
    void setup(){
        func = new MockTabulatedFunction();
    }

    @Test
    void getCount() {
        assertEquals(2,func.getCount());
    }

    @Test
    void getX() {
        assertEquals(1,func.getX(0));
    }

    @Test
    void getY() {
        assertEquals(2,func.getY(0));
    }

    @Test
    void setY() {
        func.setY(1,122);
        assertEquals(122,func.getY(1));
    }

    @Test
    void indexOfX() {
        assertEquals(0,func.indexOfX(1));
    }

    @Test
    void indexOfY() {
        assertEquals(0,func.indexOfY(2));
    }

    @Test
    void floorIndexOfX() {
        assertEquals(0,func.indexOfY(2));
    }

    @Test
    void extrapolateLeft() {
        assertEquals(-2,func.apply(-1));
    }

    @Test
    void extrapolateRight() {
        assertEquals(10,func.apply(5));
    }

    @Test
    void interpolate() {
        assertEquals(4,func.interpolate(2,2));
        assertEquals( 0.30930232558139714, func.interpolate(2, -6.4, 2.2, 13.3, 0));
    }


    @Test
    void leftBound() {
        assertEquals(1,func.leftBound());
    }

    @Test
    void rightBound() {
        assertEquals(2,func.rightBound());

    }

    @Test
    void applyTest(){
        assertEquals(4,func.apply(2));
        assertEquals(48,func.apply(24));
    }

    @Test
    void floorIndexOfXTest(){
        assertEquals(0,func.floorIndexOfX(1));
        assertEquals(2,func.floorIndexOfX(5));


    }

    @Test
    public void testArrayIteratorWithWhile() {
        Iterator<Point> iterator = func.iterator();
        int index = 0;

        while (iterator.hasNext()) {
            Point point = iterator.next();
            assertEquals(func.getX(index), point.x, 1e-9);
            assertEquals(func.getY(index), point.y, 1e-9);
            index++;
        }

        assertEquals(func.getCount(), index);
    }
}