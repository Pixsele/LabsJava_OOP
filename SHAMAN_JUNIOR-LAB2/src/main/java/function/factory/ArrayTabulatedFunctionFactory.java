package function.factory;

import function.ArrayTabulatedFunction;
import function.api.MathFunction;
import function.api.TabulatedFunction;

public class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory{
    @Override
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        return new ArrayTabulatedFunction(xValues,yValues);
    }

    @Override
    public TabulatedFunction create(MathFunction source, double xFrom, double xTo, int count) {
        return new ArrayTabulatedFunction(source,xFrom,xTo,count);
    }
}