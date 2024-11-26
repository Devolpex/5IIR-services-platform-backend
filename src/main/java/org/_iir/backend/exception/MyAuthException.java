package org._iir.backend.exception;

import java.util.List;


import org._iir.backend.handler.errors.MyErrRes;
import org._iir.backend.handler.errors.MyErrorField;
import org.springframework.security.core.AuthenticationException;

public class MyAuthException extends AuthenticationException {
    private MyErrRes response;

    public MyAuthException(String message, String field) {
        super(message);
        response.setErrors(List.of(MyErrorField.builder().message(message).field(field).build()));

    }

    public MyAuthException(String message) {
        super(message);
        response.setMessage(message);
    }

    public MyErrRes getResponse() {
        return response;
    }

}