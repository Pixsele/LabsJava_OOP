package function;

import java.util.Arrays;

public class ArrayTabulatedFunction extends AbstractTabulatedFunction implements Removable, Insertable{
    // Поля для хранения значений x и y
    private double[] xValues;
    private double[] yValues;

    // Конструктор с массивами xValues и yValues
    public ArrayTabulatedFunction(double[] xValues, double[] yValues) {

        this.xValues = Arrays.copyOf(xValues, xValues.length);
        this.yValues = Arrays.copyOf(yValues, yValues.length);
        this.count = xValues.length;
    }

    // Конструктор с параметрами source, xFrom, xTo, count
    public ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {

        // Если xFrom > xTo, поменять их местами
        if (xFrom > xTo) {
            double temp = xFrom;
            xFrom = xTo;
            xTo = temp;
        }
        this.count = count;
        this.xValues = new double[count];
        this.yValues = new double[count];

        if (xFrom == xTo) {
            // Если xFrom равняется xTo
            double yValue = source.apply(xFrom);
            for (int i = 0; i < count; ++i) {
                xValues[i] = xFrom;
                yValues[i] = yValue;
            }
        } else {
            double step = (xTo - xFrom) / (count - 1);
            for (int i = 0; i < count; ++i) {
                xValues[i] = xFrom + i * step;
                yValues[i] = source.apply(xValues[i]);
            }
        }
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double getX(int index) {
        return xValues[index];
    }

    @Override
    public double getY(int index) {
        return yValues[index];
    }

    @Override
    public void setY(int index, double value) {
        yValues[index] = value;
    }

    @Override
    public int indexOfX(double x) {
        for (int i = 0; i < getCount(); i++) {
            if (xValues[i] == x) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        for (int i = 0; i < getCount(); i++) {
            if (yValues[i] == y) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public double leftBound() {
        return xValues[0];
    }

    @Override
    public double rightBound() {
        return xValues[count - 1];
    }

    @Override
    protected int floorIndexOfX(double x) {
        for (int i = 1; i < count; i++) {
            if (x < xValues[i]) {
                return i - 1;
            }
        }
        return count - 1;
    }

    // Метод экстраполяции слева
    @Override
    protected double extrapolateLeft(double x) {
        if (count == 1) {
            return yValues[0];
        }
        return interpolate(x, xValues[0], xValues[1], yValues[0], yValues[1]);
    }

    // Метод экстраполяции справа
    @Override
    protected double extrapolateRight(double x) {
        if (count == 1) {
            return yValues[0];
        }
        return interpolate(x, xValues[count - 2], xValues[count - 1], yValues[count - 2], yValues[count - 1]);
    }

    @Override
    public double interpolate(double x, int floorIndex) {
        if (count == 1) {
            return yValues[0];
        }
        return interpolate(x, xValues[floorIndex], xValues[floorIndex + 1], yValues[floorIndex], yValues[floorIndex + 1]);
    }

    @Override
    public void insert(double x, double y) {

        int index = indexOfX(x);

        if(index != -1){
            yValues[index] = y;
            return;
        }
        else {
            double[] newArrayX = new double[count+1];
            double[] newArrayY = new double[count+1];

            if(x < this.xValues[0]) {
                newArrayX[0] = x;
                newArrayY[0] = y;

                System.arraycopy(this.xValues, 0, newArrayX, 1, count);
                System.arraycopy(this.yValues, 0, newArrayY, 1, count);
            }
            else if(x > this.xValues[count-1]){
                System.arraycopy(this.xValues, 0, newArrayX, 0, count);
                System.arraycopy(this.yValues, 0, newArrayY, 0, count);

                newArrayX[count] = x;
                newArrayY[count] = y;
            }else{
                int pos = 0;
                for(; pos < count; pos++){
                    if(this.xValues[pos] < x && x < this.xValues[pos + 1]){
                        System.arraycopy(this.xValues, 0, newArrayX, 0, pos+1);
                        System.arraycopy(this.yValues, 0, newArrayY, 0, pos+1);

                        newArrayX[pos + 1] = x;
                        newArrayY[pos + 1] = y;

                        System.arraycopy(this.xValues, pos + 1, newArrayX,pos + 2, count - pos - 1);
                        System.arraycopy(this.yValues, pos + 1, newArrayY, pos + 2, count - pos - 1);
                        break;
                    }
                }
            }

            this.xValues = newArrayX;
            this.yValues = newArrayY;
            this.count++;

            return;
        }
    }
    @Override
    public void remove(int index) {
        double[] newXValues = new double[count - 1];
        double[] newYValues = new double[count - 1];

        System.arraycopy(xValues, 0, newXValues, 0, index);
        System.arraycopy(yValues, 0, newYValues, 0, index);

        System.arraycopy(xValues, index + 1, newXValues, index, count - index - 1);
        System.arraycopy(yValues, index + 1, newYValues, index, count - index - 1);

        this.xValues = newXValues;
        this.yValues = newYValues;
        count--;
    }
}
