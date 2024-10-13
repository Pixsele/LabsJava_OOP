package operations;

import java.util.Iterator;

import function.api.TabulatedFunction;
import function.api.Iterable;
import function.Point;
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
}
