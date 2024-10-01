package function.Chaplygin;

import function.MathFunction;

import java.util.function.BiFunction;
import java.util.function.DoubleFunction;

public class ChaplyginFunction implements MathFunction {




    private final BiFunction<Double, Double, Double> ODEfunc;
    private final DoubleFunction<Double> u;
    private final DoubleFunction<Double> v;
    private final int x0;
    private final int L =1;
    private final double dx = 0.0001;



    ChaplyginFunction(BiFunction<Double, Double, Double> func, DoubleFunction<Double> Ufanc ,DoubleFunction<Double> Vfanc, int x0){
        this.ODEfunc = func;
        this.u = Ufanc;
        this.v = Vfanc;
        this.x0 = x0;
    }

    double MethodСhap(){
        DoubleFunction<Double> ud = Derivatives.derive(u);
        DoubleFunction<Double> vd = Derivatives.derive(v);

        DoubleFunction<Double> h = (x) -> ud.apply(x) - ODEfunc.apply(x,u.apply(x)); //h(x) = u'(x) - f(x,u(x))
        DoubleFunction<Double> g = (x) -> vd.apply(x) - ODEfunc.apply(x,vd.apply(x)); //g(x) = v'(x) - f(x,v(x))



        return 1;
    }






    @Override
    public double apply(double x){
        return x;
    }
}
