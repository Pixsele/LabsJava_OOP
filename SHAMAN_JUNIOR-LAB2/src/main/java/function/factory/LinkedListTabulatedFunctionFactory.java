package function.factory;

import function.LinkedListTabulatedFunction;
import function.api.MathFunction;
import function.api.TabulatedFunction;

public class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory{
    @Override
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        return new LinkedListTabulatedFunction(xValues,yValues);
    }
    @Override
    public TabulatedFunction create(MathFunction source, double xFrom, double xTo, int count) {
        return new LinkedListTabulatedFunction(source,xFrom,xTo,count);
    }
}