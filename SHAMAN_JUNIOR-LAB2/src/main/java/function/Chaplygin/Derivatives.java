package function.Chaplygin;

import java.util.function.DoubleFunction;

public class Derivatives {
    private static final double DX = 0.0001;

    public static DoubleFunction<Double> derive(DoubleFunction<Double> f) {
        return (x) -> (f.apply(x + DX) - f.apply(x)) / DX;
    }
}

