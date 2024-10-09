package function.Chaplygin;

import java.util.function.BiFunction;
import java.util.function.DoubleFunction;

public class Derivatives {
    private static final double DX = 1e-8;

    public static DoubleFunction<Double> derive(DoubleFunction<Double> f) {
        return (x) -> (f.apply(x + DX) - f.apply(x)) / DX;
    }

    public static BiFunction<Double,Double,Double> derive(BiFunction<Double,Double,Double> f ){
        return (x,y) -> (f.apply(x,y+DX) - f.apply(x,y-DX)) / (2 * DX);
    }
}

