package web.controllers;

import function.api.MathFunction;

public class AnnotatedFunctions {
    private final MathFunction function;
    private final int priority;
    private final String localizedName;

    public AnnotatedFunctions(MathFunction function, int priority, String localizedName) {
        this.function = function;
        this.priority = priority;
        this.localizedName = localizedName;
    }

    public MathFunction getFunction() {
        return function;
    }

    public int getPriority() {
        return priority;
    }

    public String getLocalizedName() {
        return localizedName;
    }
}
