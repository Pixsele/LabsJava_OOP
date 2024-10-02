package function.Chaplygin;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.DoubleFunction;

import static org.junit.jupiter.api.Assertions.*;

class ChaplyginFunctionTest {

    @Test
    void methodСhap() {
//        BiFunction<Double,Double,Double> ODE = (x,y) -> y*y - x*x;
//        DoubleFunction<Double> u = (x) -> -x;
//        DoubleFunction<Double> v = (x) -> x;
//        ChaplyginFunction obj = new ChaplyginFunction(ODE,u,v,0);
//        obj.MethodСhap(10);
    }

    @Test
    void apply() {
    }

    @Test
    void method() {
        BiFunction<Double,Double,Double> ODE = (x,y) -> y*y - x*x;
        DoubleFunction<Double> u = (x) -> -x;
        DoubleFunction<Double> v = (x) -> x;
        ChaplyginFunction obj = new ChaplyginFunction(ODE,u,v,0,10);
        obj.Method(10);

    }
}