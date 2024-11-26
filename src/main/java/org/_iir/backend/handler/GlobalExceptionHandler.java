package org._iir.backend.handler;

import org._iir.backend.exception.MyAuthException;
import org._iir.backend.handler.errors.MyErrRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class GlobalExceptionHandler {

    @ExceptionHandler(MyAuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<MyErrRes> handleMyAuthException(MyAuthException ex) {
        return new ResponseEntity(ex.getResponse(), HttpStatus.UNAUTHORIZED);
    }
}
