package org._iir.backend.exception;

import java.util.List;

import org._iir.backend.handler.OwnError;
import org._iir.backend.handler.OwnErrorResponse;

public class OwnAlreadyExistsException extends RuntimeException {
    private OwnErrorResponse response;

    public OwnAlreadyExistsException(String msg) {
        super(msg);
        this.response = OwnErrorResponse.builder().message(msg).build();
    }

    public OwnAlreadyExistsException(String msg,String field) {
        super(msg);
        this.response = OwnErrorResponse.builder().errors(List.of(OwnError.builder().message(msg).field(field).build())).build();
    }


    public OwnErrorResponse getResponse() {
        return response;
    }

}
