package function.Chaplygin;

import function.MathFunction;

import java.util.function.BiFunction;
import java.util.function.DoubleFunction;

public class ChaplyginFunction implements MathFunction {

    private final BiFunction<Double, Double, Double> ODEfunc;
    private final double u0;
    private final double v0;
    private final double x0;
    private final int n;
    private final double L = 1;

    ChaplyginFunction(BiFunction<Double, Double, Double> func, double u, double v, double x0, int steps){
        this.ODEfunc = func;
        this.u0 = u;
        this.v0 = v;
        this.x0 = x0;
        this.n = steps;
    }

    private double NextStepForU(double x,double prev){

        BiFunction<Double,Double,Double> funcd = Derivatives.derive(ODEfunc);//находим производную

        DoubleFunction<Double> FuncForIntegral = (x1) -> Math.pow(Math.E, -L*(x - x1)) * (ODEfunc.apply(x1,prev) - funcd.apply(x1,prev)); //cобираем функцию для интеграла

        Integral firstIntegral = new Integral(FuncForIntegral); //интеграл

        double uNext = prev + firstIntegral.solveByTrapezoidalRule(x,x0); //считаем новое приблежение

        return uNext;

    }

    private double NextStepForV(double x,double prev){

        BiFunction<Double,Double,Double> funcd = Derivatives.derive(ODEfunc);//находим производную

        DoubleFunction<Double> FuncForIntegral = (x1) -> Math.pow(Math.E, -L*(x - x1)) * (funcd.apply(x1,prev) - ODEfunc.apply(x1,prev)); //cобираем функцию для интеграла

        Integral firstIntegral = new Integral(FuncForIntegral); //интеграл

        double uNext = prev + firstIntegral.solveByTrapezoidalRule(x,x0); //считаем новое приблежение

        return uNext;

    }

    @Override
    public double apply(double x) {
        double newU = u0;
        double newV = v0;

        for (int i = 0; i < n; i++) {
            newU = NextStepForU(x, newU);
            newV = NextStepForV(x, newV);

            if (Math.abs(newU - newV) < 1e-6) {
                return (newU + newV) / 2;
            }
        }

        return (newU + newV) /2;
    }
}
