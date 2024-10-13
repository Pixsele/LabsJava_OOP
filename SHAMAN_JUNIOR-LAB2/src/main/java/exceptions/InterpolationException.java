package exceptions;

public class InterpolationException extends RuntimeException {
    // Конструктор без параметров
    public InterpolationException() {
        super();
    }

    public InterpolationException(String message) {
        super(message);
    }
}