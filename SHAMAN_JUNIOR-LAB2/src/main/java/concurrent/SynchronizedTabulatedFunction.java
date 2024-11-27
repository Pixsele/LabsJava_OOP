package concurrent;

import function.Point;
import function.api.TabulatedFunction;
import operations.TabulatedFunctionOperationService;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SynchronizedTabulatedFunction implements TabulatedFunction {

    private final TabulatedFunction function;

    public SynchronizedTabulatedFunction(TabulatedFunction function) {
        this.function = function;
    }

    @Override
    public int getCount() {
        synchronized (function){
            return function.getCount();
        }
    }

    @Override
    public double getX(int index) {
        synchronized (function){
            return function.getX(index);
        }
    }

    @Override
    public double getY(int index) {
        synchronized (function){
            return function.getY(index);
        }
    }

    @Override
    public void setY(int index, double value) {
        synchronized (function){
            function.setY(index,value);
        }
    }

    @Override
    public int indexOfX(double x) {
        synchronized (function){
            return function.indexOfX(x);
        }
    }

    @Override
    public int indexOfY(double y) {
        synchronized (function){
            return function.indexOfY(y);
        }
    }

    @Override
    public double leftBound() {
        synchronized (function){
            return function.leftBound();
        }
    }

    @Override
    public double rightBound() {
        synchronized (function){
            return function.rightBound();
        }
    }

    @Override
    public double[] getXValues() {
        return function.getXValues();
    }

    @Override
    public double[] getYValues() {
        return function.getYValues();
    }

    @Override
    public double apply(double x) {
        synchronized (function){
            return function.apply(x);
        }
    }

    @Override
    public Iterator<Point> iterator() {
        synchronized (function){
            Point[] points = TabulatedFunctionOperationService.asPoints(this.function);

            return new Iterator<Point>() {
                private int i = 0; // индекс

                @Override
                public boolean hasNext() {
                    return i < getCount();
                }

                @Override
                public Point next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException("No more elements available.");
                    }
                    // Создаем объект Point на основе xValues и yValues
                    Point point = new Point(points[i].x, points[i].y);
                    i++; // увеличиваем индекс
                    return point;
                }
            };

        }
    }

    @Override
    public void remove(int index) {
        function.remove(index);
    }

    public interface Operation<T> {
        T apply(SynchronizedTabulatedFunction function);
    }

    public <T> T doSynchronously(Operation<? extends T> operation) {
        synchronized (function) {
            return operation.apply(this);
        }
    }

    @Override
    public void insert(double x, double y) {
        function.insert(x,y);
    }
}

