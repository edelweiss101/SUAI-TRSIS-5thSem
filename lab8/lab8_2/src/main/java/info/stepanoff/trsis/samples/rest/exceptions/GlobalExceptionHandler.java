package info.stepanoff.trsis.samples.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обработчик UnauthorizedException (401)
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        return new ResponseEntity<>("Authorization is required", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Обработчик ForbiddenException (403)
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> handleForbiddenException(ForbiddenException ex, WebRequest request) {
        return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
    }

    /**
     * Обработчик всех остальных исключений
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
