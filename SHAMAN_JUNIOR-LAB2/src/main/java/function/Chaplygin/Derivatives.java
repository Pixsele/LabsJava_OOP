package function.Chaplygin;

import java.util.function.DoubleFunction;

public class Derivatives {
    // approximate the limit
    private static final double DX = 0.0001;

    public static DoubleFunction<Double> derive(DoubleFunction<Double> f) {
        return (x) -> (f.apply(x + DX) - f.apply(x)) / DX;
    }

    public static void main(String[] args) {
        {
            // f(x) = x^3
            DoubleFunction<Double> cube = (x) -> 2*x;

            // f'(x) = 3 * x^2
            DoubleFunction<Double> cubeDeriv = derive(cube);

            System.out.println(cubeDeriv.apply(123));

        }
    }
}

