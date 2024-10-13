package function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

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
        int res = list.floorIndexOfX(2.5);
        assertEquals(5, res);
    }

    @Test
    void floorIndexOfXBoundTest() {
        int res = list.floorIndexOfX(5);
        assertEquals(5, res);
    }

    @Test
    void removeMiddleTest() {
        list.remove(2);
        list.remove(5);
        assertEquals(5, list.getX(2));
        assertEquals(5, list.getX(5));
    }

    @Test
    void removeLeftBoundTest() {
        list.remove(0);
        assertEquals(2, list.getX(0));
    }

    @Test
    void removeRightBoundTest() {
        list.remove(list.getCount() - 1);
        assertEquals(4, list.getX(list.getCount() - 1));
    }

    @Test
    void insertTestFirstPos(){
        list.insert(0.5, 1.0);
        assertEquals(6, list.getCount());
        assertEquals(0.5, list.getX(0));
        assertEquals(1.0, list.getY(0));
    }

    @Test
    void testInsertIntoMiddle() {
        list.insert(2.0, 3.0);
        assertEquals(6, list.getCount());
        assertEquals(2.0, list.getX(1));
        assertEquals(3.0, list.getY(1));
    }

    @Test
    void testInsertIntoEnd() {
        list.insert(6.0, 12.0);
        assertEquals(6, list.getCount());
        assertEquals(4.0, list.getX(3));
        assertEquals(8.0, list.getY(3));
    }

    @Test
    void extrapilateLeftTest(){
        assertEquals(-4,list.apply(-2));
    }

    @Test
    void extrapilateRightTest(){
        assertEquals(40,list.apply(20));
    }

    @Test
    void interpolateTest(){
        assertEquals(6,list.apply(3));
        assertEquals(5,list.interpolate(2.5,1));
    }

    @Test
    void applyTest(){
        assertEquals(24,list.apply(12));
        assertEquals(15.3,list.apply(7.65));

    }

    @Test
    void iteratorTestWithWhile() {
        Iterator<Point> iterator = list.iterator();

        int i = 1;

        while(iterator.hasNext()){
            Point point = iterator.next();

            assertEquals(i,point.x);
            assertEquals(i*2,point.y);

            i++;
        }
    }

    @Test
    void iteratorTestWithFor(){
        Iterator<Point> iterator = list.iterator();

        int i = 1;

        for(Point point : list){

            assertEquals(i,point.x);
            assertEquals(i*2,point.y);

            i++;
        }
    }
}