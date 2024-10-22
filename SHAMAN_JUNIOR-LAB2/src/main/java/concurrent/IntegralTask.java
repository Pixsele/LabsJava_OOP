package concurrent;

import function.api.TabulatedFunction;

import java.util.concurrent.Callable;

public class IntegralTask implements Callable<Double> {

    private TabulatedFunction function;
    private double a,b;

    public IntegralTask(TabulatedFunction function, double a, double b) {

        if (function == null){
            throw new IllegalArgumentException("Function is null");
        }

        this.function = function;
        this.a = a;
        this.b = b;
    }

    public double solveByTrapezoidalRule(int n){

        double h = (b - a)/n;
        double result = 0.5 * (function.apply(a) + function.apply(b));

        for(int i = 1;i < n;i++){
            double newx = a + i * h;
            result += function.apply(newx);
        }
        result = result * h;

        return result;
    }

    public Double call(){
        return solveByTrapezoidalRule(1000);
    }

}
