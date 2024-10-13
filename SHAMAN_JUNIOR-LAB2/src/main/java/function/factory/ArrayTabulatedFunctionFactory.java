package function.factory;

import function.ArrayTabulatedFunction;
import function.api.TabulatedFunction;

public class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory{
    @Override
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        return new ArrayTabulatedFunction(xValues,yValues);
    }
}