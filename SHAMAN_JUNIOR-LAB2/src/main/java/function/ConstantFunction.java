package function;

import function.api.MathFunction;

public class ConstantFunction implements MathFunction {
    private final double consta;

    //конструктор
    public ConstantFunction(double x) {
        this.consta = x;
    }

    // Метод apply, который всегда возвращает константу
    @Override
    public double apply(double x) {
        return consta;
    }

    // Геттер для получения значения константы
    public double getConstant() {
        return consta;
    }
}

