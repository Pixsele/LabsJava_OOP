package operations;

import java.util.Iterator;

import function.Point;
import exceptions.InconsistentFunctionsException;
import function.factory.ArrayTabulatedFunctionFactory;
import function.factory.TabulatedFunctionFactory;

import function.api.TabulatedFunction;

public class TabulatedFunctionOperationService {
    public static Point[] asPoints(TabulatedFunction tabulatedFunction) {
        // Инициализация массива для хранения точек
        Point[] points = new Point[tabulatedFunction.getCount()];

        // Индекс для записи точек в массив
        int i = 0;

        // Цикл for-each для итерирования по табулированной функции
        for (Point point : tabulatedFunction) {
            points[i] = point;
            i++; // Инкремент индекса для следующей точки
        }

        return points;
    }
    private TabulatedFunctionFactory factory;

    // Конструктор с фабрикой
    public TabulatedFunctionOperationService(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    // Конструктор по умолчанию, использующий ArrayTabulatedFunctionFactory
    public TabulatedFunctionOperationService() {
        this.factory = new ArrayTabulatedFunctionFactory();
    }

    // Геттер для фабрики
    public TabulatedFunctionFactory getFactory() {
        return factory;
    }

    // Сеттер для фабрики
    public void setFactory(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    // Вложенный интерфейс для бинарных операций
    private interface BiOperation {
        double apply(double u, double v);
    }

    // Метод для выполнения бинарной операции над двумя функциями
    private TabulatedFunction doOperation(TabulatedFunction a, TabulatedFunction b, BiOperation operation) {
        // Проверка на совместимость функций
        if (a.getCount() != b.getCount()) {
            throw new InconsistentFunctionsException("У функций разное количество точек");
        }

        Point[] pointsA = asPoints(a);
        Point[] pointsB = asPoints(b);

        double[] xValues = new double[a.getCount()];
        double[] yValues = new double[a.getCount()];

        for (int i = 0; i < a.getCount(); i++) {
            if (pointsA[i].x != pointsB[i].x) {
                throw new InconsistentFunctionsException("У функций разные значения X");
            }
            xValues[i] = pointsA[i].x;
            yValues[i] = operation.apply(pointsA[i].y, pointsB[i].y); // Применение операции
        }

        return factory.create(xValues, yValues); // Создание новой функции с использованием фабрики
    }

    // Метод для сложения двух функций
    public TabulatedFunction add(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, (u, v) -> u + v);
    }

    // Метод для вычитания двух функций
    public TabulatedFunction subtract(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, (u, v) -> u - v);
    }

    public TabulatedFunction multiply(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, (u, v) -> u * v);
    }

    public TabulatedFunction devide(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, (u, v) -> u / v);
    }

}
