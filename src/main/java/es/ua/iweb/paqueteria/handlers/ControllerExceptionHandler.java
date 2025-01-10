package es.ua.iweb.paqueteria.handlers;

import es.ua.iweb.paqueteria.constants.ErrorMessages;
import es.ua.iweb.paqueteria.exception.*;
import es.ua.iweb.paqueteria.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {
    private final static String METHOD_ARGUMENT_NOT_VALID_KEY = "METHOD_ARGUMENT_NOT_VALID_KEY";
    private final static String INVALID_JSON = "INVALID_JSON";
    private final static String USER_NOT_FOUND = "USER_NOT_FOUND";
    private final static String RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND";
    private final static String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorResponse> handleValidationExceptions(final MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map((error) ->
                        ErrorResponse.of(getMessage(error), METHOD_ARGUMENT_NOT_VALID_KEY)
                )
                .toList();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public List<ErrorResponse> handleHttpMessageNotReadableException(final HttpMessageNotReadableException ex) {
        return List.of(ErrorResponse.of(ErrorMessages.INVALID_JSON, INVALID_JSON));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public List<ErrorResponse> handleDataNotFoundException(final DataNotFoundException ex) {
        return ex.toList();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BadCredentialsException.class)
    public List<ErrorResponse> handleBadCredentialsException(final BadCredentialsException ex) {
        return List.of(ErrorResponse.of(ErrorMessages.USER_NOT_FOUND, USER_NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public List<ErrorResponse> handleUnauthorizedException(final UnauthorizedException ex) {
        return ex.toList();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTokenException.class)
    public List<ErrorResponse> handleInvalidTokenException(final InvalidTokenException ex) {
        return ex.toList();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public List<ErrorResponse> handleConflictException(final ConflictException ex) {
        return ex.toList();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MalformedObjectException.class)
    public List<ErrorResponse> handleMalformedJsonException(final MalformedObjectException ex) {
        return ex.toList();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public List<ErrorResponse> handleNoResourceFoundException(final NoResourceFoundException ex) {
        return List.of(ErrorResponse.of(ErrorMessages.RESOURCE_NOT_FOUND, RESOURCE_NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public List<ErrorResponse> handleException(final Exception ex) {
        ex.printStackTrace();
        return List.of(ErrorResponse.of(ErrorMessages.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR));
    }

    private static String getMessage(ObjectError error) {
        return String.format(
                error.getDefaultMessage() == null ? ErrorMessages.FIELD_IS_NOT_VALID : error.getDefaultMessage(), ((FieldError) error).getField()
        );
    }
}
