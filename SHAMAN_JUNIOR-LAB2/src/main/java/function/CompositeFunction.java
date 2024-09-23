package function;

public class CompositeFunction {
    private MathFunction firstFunc,secondFunc;

    public CompositeFunction(MathFunction first, MathFunction second){
        this.firstFunc = first;
        this.secondFunc = second;
    }

    double apply(double x){
        return secondFunc.apply(firstFunc.apply(x));
    }

}
