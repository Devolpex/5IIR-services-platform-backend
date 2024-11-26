package org._iir.backend.handler;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org._iir.backend.exception.OwnNotSaveException;
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
    public ResponseEntity<OwnErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<OwnError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::mapFieldError)
                .collect(Collectors.toList());

        OwnErrorResponse errorResponse = OwnErrorResponse.builder()
                .errors(errors)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
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

    // // Handle OwnNotFoundException
    // @ExceptionHandler(OwnNotFoundException.class)
    // @ResponseStatus(HttpStatus.NOT_FOUND)
    // public ResponseEntity<OwnErrorResponse> handleOwnNotFoundException(OwnNotFoundException ex) {
    //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getResponse());
    // }

    // // Handle OwnNotDeleteException
    // @ExceptionHandler(OwnNotDeleteException.class)
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // public ResponseEntity<OwnErrorResponse> handleOwnNotDeleteException(OwnNotDeleteException ex) {
    //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getResponse());
    // }
}
