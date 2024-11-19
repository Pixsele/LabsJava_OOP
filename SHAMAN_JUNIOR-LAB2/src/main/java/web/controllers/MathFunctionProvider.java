package web.controllers;

import function.ConstantFunction;
import function.IdentityFunction;
import function.SqrFunction;
import function.api.MathFunction;

import java.util.Map;
import java.util.TreeMap;

public class MathFunctionProvider {


    public static Map<String, MathFunction> mathFunctions(){

        Map<String, MathFunction> mathFunctions = new TreeMap<>();

        mathFunctions.put("Квадратичная функция", new SqrFunction());
        mathFunctions.put("Константная функция", new ConstantFunction());
        mathFunctions.put("Тождественная функция", new IdentityFunction());

        return mathFunctions;
    };
}
