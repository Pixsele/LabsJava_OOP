package operations;

import function.api.MathFunction;

public interface DifferentialOperator<T> extends MathFunction {
    T derive (T function);
}
