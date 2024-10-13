package function;

import function.api.MathFunction;

public class CompositeFunction implements MathFunction {
    private final MathFunction firstFunc,secondFunc;

    public CompositeFunction(MathFunction first, MathFunction second){
        this.firstFunc = first;
        this.secondFunc = second;
    }
    @Override
    public double apply(double x){
        return secondFunc.apply(firstFunc.apply(x));
    }

}
