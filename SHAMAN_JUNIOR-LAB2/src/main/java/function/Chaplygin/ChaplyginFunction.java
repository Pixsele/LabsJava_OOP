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

        DoubleFunction<Double> funcd = Derivatives.derive(func);

        DoubleFunction<Double> h = (x) -> funcd.apply(x) - ODEfunc.apply(x,func.apply(x)); //h(x) = u'(x) - f(x,u(x))

        DoubleFunction<Double> FuncWh = (x) -> Math.pow(Math.E, x) * h.apply(x);

        Integral firstIntegral = new Integral(FuncWh);

        DoubleFunction<Double> uNext = (x) -> func.apply(x) - 1/Math.E * firstIntegral.solveByTrapezoidalRule(x,x0);

        return uNext;
    }

    void Method(double testX){
        DoubleFunction<Double> newU = u;
        DoubleFunction<Double> newV = v;
        for(int i = 0;i < n;i++){
            newU = MethodChaplyginForOneFunction(newU);
            newV = MethodChaplyginForOneFunction(newV);
        }
        System.out.println(newU.apply(testX));
        System.out.println(newV.apply(testX));

    }

//    double MethodСhap(double xTest){
//        DoubleFunction<Double> ud = Derivatives.derive(u);
//        DoubleFunction<Double> vd = Derivatives.derive(v);
//
//        DoubleFunction<Double> h = (x) -> ud.apply(x) - ODEfunc.apply(x,u.apply(x)); //h(x) = u'(x) - f(x,u(x))
//        DoubleFunction<Double> g = (x) -> vd.apply(x) - ODEfunc.apply(x,v.apply(x)); //g(x) = v'(x) - f(x,v(x))
//
//        DoubleFunction<Double> FuncWh = (x) -> Math.pow(Math.E, x) * h.apply(x);
//        DoubleFunction<Double> FuncWg = (x) -> Math.pow(Math.E, x) * g.apply(x);
//
//        Integral firstIntegral = new Integral(FuncWh);
//        Integral secondIntegral = new Integral(FuncWg);
//
//        DoubleFunction<Double> uNext = (x) -> u.apply(x) - 1/Math.E * firstIntegral.solveByTrapezoidalRule(x,x0);
//        DoubleFunction<Double> vNext = (x) -> v.apply(x) - 1/Math.E * secondIntegral.solveByTrapezoidalRule(x,x0);
//
//        return 1;
//    }






    @Override
    public double apply(double x){
        return x;
    }
}
