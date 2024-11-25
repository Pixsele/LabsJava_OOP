package exceptions;

public class LoadFunctionExecption extends RuntimeException {
    public LoadFunctionExecption() {
        super();
    }

    public LoadFunctionExecption(String message) {
        super(message);
    }
}
