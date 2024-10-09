package function.Chaplygin;

import function.MathFunction;

import java.util.function.BiFunction;
import java.util.function.DoubleFunction;

public class ChaplyginFunction implements MathFunction {

    private final BiFunction<Double, Double, Double> ODEfunc;
    private final DoubleFunction<Double> u;
    private final DoubleFunction<Double> v;
    private final double x0;
    private final int n;

    ChaplyginFunction(BiFunction<Double, Double, Double> func, DoubleFunction<Double> Ufanc ,DoubleFunction<Double> Vfanc, double x0, int n){
        this.ODEfunc = func;
        this.u = Ufanc;
        this.v = Vfanc;
        this.x0 = x0;
        this.n = n;
    }

    private DoubleFunction<Double> MethodChaplyginForOneFunction(DoubleFunction<Double> func){

        DoubleFunction<Double> funcd = Derivatives.derive(func);//находим производную

        DoubleFunction<Double> h = (x) -> funcd.apply(x) - ODEfunc.apply(x,func.apply(x)); //h(x) = u'(x) - f(x,u(x))

        DoubleFunction<Double> FuncForIntegral = (x) -> Math.pow(Math.E, x) * h.apply(x); //cобираем функцию для интеграла

        Integral firstIntegral = new Integral(FuncForIntegral); //интеграл

        DoubleFunction<Double> funcNext = (x) -> func.apply(x) - 1/Math.E * firstIntegral.solveByTrapezoidalRule(x,x0); //считаем новое приблежение

        return funcNext;
    }

    double[] Method(double testX){
        DoubleFunction<Double> newU = u;
        DoubleFunction<Double> newV = v;

        for(int i = 0;i < n;i++){
            newU = MethodChaplyginForOneFunction(newU);
            newV = MethodChaplyginForOneFunction(newV);
        }

        double[] result = {newU.apply(testX), newU.apply(testX)};
        return result;
    }

    @Override
    public double apply(double x){
        return x;
    }
}
