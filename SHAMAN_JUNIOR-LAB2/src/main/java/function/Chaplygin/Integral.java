package function.Chaplygin;

import java.util.function.DoubleFunction;

public class Integral {

    private final DoubleFunction<Double> f;

    Integral(DoubleFunction<Double> f){
        this.f = f;
    }

    double solveByTrapezoidalRule(double x, double x0){
        int n = 10;
        double h = (x - x0)/n;
        double result = 0.5 * (f.apply(x0) + f.apply(x));

        for(int i = 1;i < n;i++){
            double newx = x0 + i * h;
            result += f.apply(newx);
        }
        result = result * h;

        return result;
    }
}
