package exceptions;

public class RemoveIncorrectPoint extends RuntimeException {
    public RemoveIncorrectPoint() {}

    public RemoveIncorrectPoint(String message) {
        super(message);
    }
}
