package operations;

import function.api.MathFunction;

public abstract class SteppingDifferentialOperator implements DifferentialOperator<MathFunction>{
    protected double step;

    public SteppingDifferentialOperator(double step) {
        if (step <= 0 || Double.isInfinite(step) || Double.isNaN(step)) {
            throw new IllegalArgumentException("Step must be a positive, finite number.");
        }
        this.step = step;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        if (step <= 0 || Double.isInfinite(step) || Double.isNaN(step)) {
            throw new IllegalArgumentException("Step must be a positive, finite number.");
        }
        this.step = step;
    }

    // Абстрактный метод derive() для реализации в подклассах
    @Override
    public abstract MathFunction derive(MathFunction function);
}
