package function;

import function.api.MathFunction;

public class SqrFunction implements MathFunction {
    @Override
    public double apply(double x) {
        return Math.pow(x, 2);
    }
}
