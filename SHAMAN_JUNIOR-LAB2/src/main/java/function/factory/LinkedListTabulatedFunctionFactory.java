package function.factory;

import function.LinkedListTabulatedFunction;
import function.api.TabulatedFunction;

public class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory{
    @Override
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        return new LinkedListTabulatedFunction(xValues,yValues);
    }
}