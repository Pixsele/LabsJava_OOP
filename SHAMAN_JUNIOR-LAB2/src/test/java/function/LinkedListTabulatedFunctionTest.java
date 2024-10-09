package function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTabulatedFunctionTest {
    private LinkedListTabulatedFunction list;
    private double[] xVal = {1,2,3,4,5};
    private double[] yVal = {2,4,6,8,10};



    @Test
    void TestCreateList(){
        MathFunction func = (x) -> 2*x;
        list = new LinkedListTabulatedFunction(func,1,10,10);
        for(int i = 0;i<10;i++){
            System.out.println(list.getY(i));
        }
    }

    @Test
    void TestCreateListReverse(){
        MathFunction func = (x) -> 2*x;
        list = new LinkedListTabulatedFunction(func,10,1,10);
        for(int i = 0;i<10;i++){
            System.out.println(list.getY(i));
        }
    }

    @Test
    void TestCreateListNotDiff(){
        MathFunction func = (x) -> 2*x;
        list = new LinkedListTabulatedFunction(func,10,10,10);
        for(int i = 0;i<10;i++){
            System.out.println(list.getY(i));
        }
    }

    @Test
    void TestCreateListFromMassive(){
        list = new LinkedListTabulatedFunction(xVal,yVal);
        for(int i = 0;i<5;i++){
            System.out.println(list.getY(i));
        }
    }

    @BeforeEach
    void CreateFromMassive() {
        list = new LinkedListTabulatedFunction(xVal, yVal);
    }


    @Test
    void getCount() {
        assertEquals(5,list.getCount());
    }

    @Test
    void leftBound() {
        assertEquals(1,list.leftBound());
    }

    @Test
    void rightBound() {
        assertEquals(5,list.rightBound());

    }

    @Test
    void getX() {
        assertEquals(1,list.getX(0));
    }

    @Test
    void getY() {
        assertEquals(2,list.getY(0));
    }

    @Test
    void setY() {
        list.setY(1,12);
        assertEquals(12,list.getY(1));
    }

    @Test
    void indexOfXInclude() {
        int res = list.indexOfX(4);
        assertEquals(3, res);
    }

    @Test
    void indexOfXNotInclude() {
        int res = list.indexOfX(1000);
        assertEquals(-1, res);
    }

    @Test
    void indexOfYInclude() {
        int res = list.indexOfY(4);
        assertEquals(1, res);
    }

    @Test
    void indexOfYNotInclude() {
        int res = list.indexOfY(1000);
        assertEquals(-1, res);
    }

    @Test
    void floorIndexOfXMiddleTest() {
        int res = list.floorIndexOfX(5.5);
        assertEquals(5, res);
    }

    @Test
    void floorIndexOfXBoundTest() {
        int res = list.floorIndexOfX(100);
        assertEquals(5, res);
    }


    void removeMiddleTest() {
        list.remove(2);
        list.remove(5);
        assertEquals(3, list.getX(2));
        assertEquals(7, list.getX(5));
    }


    void removeLeftBoundTest() {
        list.remove(0);
        assertEquals(1, list.getX(0));
    }


    void removeRightBoundTest() {
        list.remove(list.getCount() - 1);
        assertEquals(6, list.getX(list.getCount() - 1));
    }
}