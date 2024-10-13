package function.Chaplygin;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

class ChaplyginFunctionTest {


    @Test
    void applyTest1() {

        BiFunction<Double,Double,Double> func = (x,y) -> x + y;
        ChaplyginFunction chap = new ChaplyginFunction(func,0.5,1,0,10);

        double res = chap.apply(1.0);
        assertEquals(6.043865,res,1e-6);

    }
    @Test
    void applyTest2(){
        BiFunction<Double,Double,Double> func = (x,y) -> x * y;
        ChaplyginFunction chap = new ChaplyginFunction(func,1,1,0,100);

        double res = chap.apply(1.0);
        assertEquals(1.0,res,1e-6);
    }

    @Test
    void applyTestWithSin(){
        BiFunction<Double, Double, Double> func = (x, y) -> Math.sin(x + y);
        ChaplyginFunction chap = new ChaplyginFunction(func,1,1,0,100);

        double res = chap.apply(Math.PI);
        assertEquals(-0.01644671,res,1e-6);
    }
}