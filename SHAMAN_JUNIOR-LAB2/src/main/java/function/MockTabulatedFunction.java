package function;

import java.util.Iterator;

public class MockTabulatedFunction extends AbstractTabulatedFunction{

    private double[] arrayX;
    private  double[] arrayY;
    private int count;
    public MockTabulatedFunction(){
        this.arrayX = new double[2];
        this.arrayY = new double[2];

        arrayX[0] = 1;
        arrayX[1] = 2;

        arrayY[0] = 2;
        arrayY[1] = 4;

        count = 2;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double getX(int index) {
        return arrayX[index];
    }

    @Override
    public double getY(int index) {
        return arrayY[index];
    }

    @Override
    public void setY(int index, double value) {
        arrayY[index] = value;
    }

    @Override
    public int indexOfX(double x) {
        for(int i = 0;i < count;i++){
            if(x == arrayX[i]){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        for(int i = 0;i < count;i++){
            if(y == arrayY[i]){
                return i;
            }
        }
        return -1;
    }

    @Override
    protected int floorIndexOfX(double x) {
        if (x > arrayX[1]) return count;
        if (indexOfX(x) != -1) return indexOfX(x);
        return 0;
    }

    @Override
    protected double extrapolateLeft(double x) {
        return arrayY[0] + (arrayY[1] - arrayY[0]) / (arrayX[1] - arrayX[0]) * (x - arrayX[0]);
    }

    @Override
    protected double extrapolateRight(double x) {
        return arrayY[0] + (arrayY[1] - arrayY[0]) / (arrayX[1] - arrayX[0]) * (x - arrayX[0]);
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        return arrayY[0] + (arrayY[1] - arrayY[0]) / (arrayX[1] - arrayX[0]) * (x - arrayX[0]);
    }

    public double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return super.interpolate(x, leftX, rightX, leftY, rightY);
    }

    @Override
    public double leftBound() {
        return arrayX[0];
    }

    @Override
    public double rightBound() {
        return arrayX[1];
    }

    @Override
    public Iterator<Point> iterator() {
        //TODO попка
        return null;
    }
}
