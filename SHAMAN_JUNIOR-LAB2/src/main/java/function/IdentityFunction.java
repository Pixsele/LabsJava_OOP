package function;

import function.api.MathFunction;

@FunctionInfo(name = "Тождественная функция", priority = 2)
public class IdentityFunction implements MathFunction {
    @Override
    public double apply(double x){
        return x;
    }
}

