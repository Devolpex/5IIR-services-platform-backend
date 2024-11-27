package org._iir.backend.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org._iir.backend.exception.MyAuthException;
import org._iir.backend.exception.OwnAlreadyExistsException;
import org._iir.backend.exception.OwnNotSaveException;
import org._iir.backend.handler.errors.MyErrRes;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<OwnError>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<OwnError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::mapFieldError)
                .collect(Collectors.toList());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Helper method to map field errors
    private OwnError mapFieldError(FieldError fieldError) {
        return OwnError.builder()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .build();
    }

    // Handle OwnNotSaveException
    @ExceptionHandler(OwnNotSaveException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<OwnErrorResponse> handleOwnNotSaveException(OwnNotSaveException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getResponse());
    }

    // Handle MyAuthException
    @ExceptionHandler(MyAuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<MyErrRes> handleMyAuthException(MyAuthException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getResponse());
    }

    // Handle OwnAlreadyExistsException
    @ExceptionHandler(OwnAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<OwnErrorResponse> handleOwnAlreadyExistsException(OwnAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getResponse());
    }
}
