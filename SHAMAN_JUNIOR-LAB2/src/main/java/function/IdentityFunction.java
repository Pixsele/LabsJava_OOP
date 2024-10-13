package function;

import function.api.MathFunction;

public class IdentityFunction implements MathFunction {
    @Override
    public double apply(double x){
        return x;
    }
}

