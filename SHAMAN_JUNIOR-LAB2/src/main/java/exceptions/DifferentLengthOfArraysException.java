package exceptions;

public class DifferentLengthOfArraysException extends RuntimeException {
  // Конструктор без параметров
  public DifferentLengthOfArraysException() {
    super();
  }

  public DifferentLengthOfArraysException(String message) {
    super(message);
  }
}
