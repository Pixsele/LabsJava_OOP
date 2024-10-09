package function.Chaplygin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.DoubleFunction;

import static org.junit.jupiter.api.Assertions.*;

class ChaplyginFunctionTest {


    @Test
    void apply() {

        BiFunction<Double,Double,Double> func = (x,y) -> x + y;
        ChaplyginFunction chap = new ChaplyginFunction(func,0.5,1,0,10);

        double res = chap.apply(1.0);
        assertEquals(6.043865,res,1e-6);

    }
}