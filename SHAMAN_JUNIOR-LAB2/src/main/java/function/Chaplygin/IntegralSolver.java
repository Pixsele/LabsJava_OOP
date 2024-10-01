package function.Chaplygin;

import java.util.function.DoubleFunction;

public class IntegralSolver {


    private DoubleFunction<Double> f;
    private  DoubleFunction<Double> g;

    IntegralSolver(DoubleFunction<Double> f, DoubleFunction<Double> g){
        this.f = f;
        this.g = g;
    }

    public DoubleFunction<Double> solve(){

        DoubleFunction<Double> du = Derivatives.derive(g);

        DoubleFunction<Double> newFunc = (x) -> g.apply(x)*f.apply(x);

        if(du.apply(0) - g.apply(0) == du.apply(0)){

        };


        return g;
    }




}
