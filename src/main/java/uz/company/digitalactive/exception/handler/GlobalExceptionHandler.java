package uz.company.digitalactive.exception.handler;

import static org.springframework.http.HttpStatus.*;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import javax.naming.ServiceUnavailableException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import uz.company.digitalactive.exception.*;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger LOGGER = LogManager.getLogger();

  @ResponseStatus(FORBIDDEN)
  @ExceptionHandler({
    ForbiddenException.class,
    CreateForbiddenException.class,
    DeleteForbiddenException.class,
    ListForbiddenException.class
  })
  public final ResponseEntity<ExceptionResponse> handleForbiddenException(
      final ForbiddenException e, final WebRequest request) {
    return constructExceptionResponse(e, request, FORBIDDEN);
  }

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ExceptionResponse> handleException(
      final Exception e, final WebRequest request) {
    return constructExceptionResponse(e, request, INTERNAL_SERVER_ERROR);
  }

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(IOException.class)
  public ResponseEntity<ExceptionResponse> handleIOException(
      final IOException e, final WebRequest request) {
    return constructExceptionResponse(e, request, INTERNAL_SERVER_ERROR);
  }

  @ResponseStatus(UNPROCESSABLE_ENTITY)
  @ExceptionHandler({JsonParsingException.class, DecodingException.class})
  public final ResponseEntity<ExceptionResponse> handleJsonException(
      final Exception e, final WebRequest request) {
    return constructExceptionResponse(e, request, UNPROCESSABLE_ENTITY);
  }

  @ResponseStatus(NOT_FOUND)
  @ExceptionHandler({NotFoundException.class, NoSuchElementException.class})
  public ResponseEntity<ExceptionResponse> handleNotFoundException(
      final NoSuchElementException e, final WebRequest request) {
    return constructExceptionResponse(e, request, NOT_FOUND);
  }

  @ResponseStatus(UNAUTHORIZED)
  @ExceptionHandler({
    InvalidCredentialsException.class,
    InvalidTokenException.class,
    UnauthorizedException.class
  })
  public final ResponseEntity<ExceptionResponse> handleUnauthorizedException(
      final UnauthorizedException e, final WebRequest request) {
    return constructExceptionResponse(e, request, UNAUTHORIZED);
  }

  @ResponseStatus(SERVICE_UNAVAILABLE)
  @ExceptionHandler(ServiceUnavailableException.class)
  public ResponseEntity<ExceptionResponse> handleServiceUnavailableException(
      final ServiceUnavailableException e, final WebRequest request) {
    return constructExceptionResponse(e, request, SERVICE_UNAVAILABLE);
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler({
    ApiException.class,
    AlreadyExistsException.class,
    DateTimeParseException.class,
    UnsupportedOperationException.class,
    IllegalArgumentException.class,
    IllegalStateException.class,
    NullPointerException.class,
    RuntimeException.class,
    InvalidArgumentException.class,
    InvalidOperationException.class,
    InvalidDBCommandException.class
  })
  public ResponseEntity<ExceptionResponse> handleBadRequests(
      final RuntimeException e, final WebRequest request) {
    return constructExceptionResponse(e, request, BAD_REQUEST);
  }

  protected ResponseEntity<ExceptionResponse> constructExceptionResponse(
      final Exception e, final WebRequest request, final HttpStatus status) {
    final String path = request.getDescription(false);

    LOGGER.error("Failed to request [{}] path. Error:", path, e);

    return new ResponseEntity<>(
        new ExceptionResponseBuilder().exception(e).path(path).status(status).build(), status);
  }
}
