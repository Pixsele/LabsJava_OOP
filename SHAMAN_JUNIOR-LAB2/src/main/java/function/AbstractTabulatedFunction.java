package function;

import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;

import function.api.TabulatedFunction;

import java.io.Serializable;

public abstract class AbstractTabulatedFunction implements TabulatedFunction, Serializable {

    // Приватное поле count для количества строк в таблице
    protected int count;

    // Метод для получения количества элементов
    public abstract int getCount();

    // Метод для поиска индекса x, который меньше заданного, но максимален из всех меньших
    protected abstract int floorIndexOfX(double x);

    // Экстраполяция слева
    protected abstract double extrapolateLeft(double x);

    // Экстраполяция справа
    protected abstract double extrapolateRight(double x);

    // Интерполяция с указанием индекса интервала
    protected abstract double interpolate(double x, int floorIndex);

    // Реализация интерполяции через линейную формулу
    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return leftY + (rightY - leftY) * (x - leftX) / (rightX - leftX);
    }

    // Реализация метода apply() из интерфейса MathFunction
    @Override
    public double apply(double x) {
        if (x < getX(0)) {
            // Левее границы - экстраполяция слева
            return extrapolateLeft(x);
        } else if (x > getX(getCount() - 1)) {
            // Правее границы - экстраполяция справа
            return extrapolateRight(x);
        } else {
            // Проверка на наличие x в таблице
            int index = indexOfX(x);
            if (index != -1) {
                return getY(index); // Если x найден, возвращаем y
            } else {
                // Интерполяция внутри интервала
                int floorIndex = floorIndexOfX(x);
                return interpolate(x, floorIndex);
            }
        }
    }
    // Метод проверки одинаковой длины массивов
    public static void checkLengthIsTheSame(double[] xValues, double[] yValues) {
        if (xValues.length != yValues.length) {
            throw new DifferentLengthOfArraysException("Lengths of xValues and yValues are not the same.");
        }
    }

    // Метод проверки сортировки массива
    public static void checkSorted(double[] xValues) {
        for (int i = 1; i < xValues.length; i++) {
            if (xValues[i] <= xValues[i - 1]) {
                throw new ArrayIsNotSortedException("Массив не отсортирован");
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(this.getClass().getSimpleName() + " size = " + this.count + "\n");

        for(Point point:this){
            str.append("[").append(point.x).append("; ").append(point.y).append("]\n");
        }

        return str.toString();
    }
}