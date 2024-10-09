package function;

public interface MathFunction {
    default CompositeFunction andThen(MathFunction afterFunction){
        return new CompositeFunction(this, afterFunction);
    }
    double apply(double x);
}

