package function;

public class CompositeFunction implements MathFunction {
    private MathFunction firstFunc,secondFunc;

    public CompositeFunction(MathFunction first, MathFunction second){
        this.firstFunc = first;
        this.secondFunc = second;
    }

    public double apply(double x){
        return secondFunc.apply(firstFunc.apply(x));
    }

}
