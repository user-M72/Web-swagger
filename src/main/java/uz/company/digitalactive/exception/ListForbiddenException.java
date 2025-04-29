package uz.company.digitalactive.exception;

public class ListForbiddenException extends RuntimeException {
  public ListForbiddenException(String message) {
    super(message);
  }

  public ListForbiddenException(String message, Throwable cause) {
    super(message, cause);
  }
}
