package function.api;

import function.CompositeFunction;

public interface MathFunction {
    default CompositeFunction andThen(MathFunction afterFunction){
        return new CompositeFunction(this, afterFunction);
    }
    double apply(double x);
}

