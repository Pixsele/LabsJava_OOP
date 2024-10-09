package function;

public abstract class AbstractTabuletedFunction implements TabulatedFunction {

    // Приватное поле count для количества строк в таблице
    private int count;

    // Конструктор для инициализации поля count
    protected AbstractTabuletedFunction(int count) {
        this.count = count;
    }

    // Метод для получения количества элементов
    public int getCount() {
        return count;
    }

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
}