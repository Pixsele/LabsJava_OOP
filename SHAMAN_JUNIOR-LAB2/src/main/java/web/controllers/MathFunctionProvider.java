package web.controllers;

import function.ConstantFunction;
import function.FunctionInfo;
import function.IdentityFunction;
import function.SqrFunction;
import function.api.MathFunction;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class MathFunctionProvider {


    public static Map<String, MathFunction> mathFunctions(){

        Map<String, MathFunction> mathFunctions = new TreeMap<>();

        mathFunctions.put("Квадратичная функция", new SqrFunction());
        mathFunctions.put("Константная функция", new ConstantFunction());
        mathFunctions.put("Тождественная функция", new IdentityFunction());

        return mathFunctions;
    };

    public static List<AnnotatedFunctions> loadFunctions(String packageName) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<AnnotatedFunctions> functions = new ArrayList<>();

        try {
            // Находим классы в указанном пакете (нужна библиотека, например, Reflections)
            Reflections reflections = new Reflections(packageName);

            // Получаем все классы с аннотацией FunctionInfo
            Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(FunctionInfo.class);

            for (Class<?> clazz : annotatedClasses) {
                if (MathFunction.class.isAssignableFrom(clazz)) {
                    FunctionInfo info = clazz.getAnnotation(FunctionInfo.class);
                    MathFunction function = (MathFunction) clazz.getDeclaredConstructor().newInstance();
                    AnnotatedFunctions newFunc = new AnnotatedFunctions(function,info.priority(),info.name());
                    functions.add(newFunc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return functions;
    };
}
