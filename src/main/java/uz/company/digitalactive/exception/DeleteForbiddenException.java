package uz.company.digitalactive.exception;

public class DeleteForbiddenException extends RuntimeException {
  public DeleteForbiddenException(String message) {
    super(message);
  }

  public DeleteForbiddenException(String message, Throwable cause) {
    super(message, cause);
  }
}
