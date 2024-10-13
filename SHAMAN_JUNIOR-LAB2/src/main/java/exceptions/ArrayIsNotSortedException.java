package exceptions;

public class ArrayIsNotSortedException extends RuntimeException {
    // Конструктор без параметров
    public ArrayIsNotSortedException() {
        super();
    }

    public ArrayIsNotSortedException(String message) {
        super(message);
    }
}
