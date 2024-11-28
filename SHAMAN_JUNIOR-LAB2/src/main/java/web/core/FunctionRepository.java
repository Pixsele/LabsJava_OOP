package web.core;

import function.api.MathFunction;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FunctionRepository {

    private final List<AnnotatedFunctions> staticFunctions = new ArrayList<>();
    private final List<AnnotatedFunctions> userFunctions = new ArrayList<>();

    public FunctionRepository() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        staticFunctions.addAll(MathFunctionProvider.loadFunctions("function"));
    }

    public void addUserFunction(AnnotatedFunctions function) {
        userFunctions.add(function);
    }

    public MathFunction getFunction(String name) {
        for (AnnotatedFunctions function : staticFunctions) {
            if (function.getLocalizedName().equals(name)) {
                return function.getFunction();
            }
        }

        // Проверяем пользовательские функции
        for (AnnotatedFunctions function : userFunctions) {
            if (function.getLocalizedName().equals(name)) {
                return function.getFunction();
            }
        }
        return null;
    }

    public List<AnnotatedFunctions> getAllFunctions() {
        List<AnnotatedFunctions> allFunctions = new ArrayList<>();
        allFunctions.addAll(staticFunctions);
        allFunctions.addAll(userFunctions);
        return allFunctions;
    }

    public Map<String, MathFunction> getFunctionMap() {
        List<AnnotatedFunctions> allFunctions = new ArrayList<>();
        allFunctions.addAll(staticFunctions);
        allFunctions.addAll(userFunctions);

        return allFunctions.stream()
                .sorted(Comparator.comparingInt(AnnotatedFunctions::getPriority)
                        .thenComparing(AnnotatedFunctions::getLocalizedName))
                .collect(Collectors.toMap(
                        AnnotatedFunctions::getLocalizedName, // Ключ: локализованное имя функции
                        AnnotatedFunctions::getFunction,      // Значение: сама функция
                        (existing, replacement) -> existing,   // Если два элемента с одинаковым ключом — оставить первый
                        LinkedHashMap::new));                  // Сохраняем порядок сортировки
    }
}
