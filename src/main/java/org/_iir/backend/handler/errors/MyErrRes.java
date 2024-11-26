package org._iir.backend.handler.errors;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyErrRes {

    private String message;
    private List<MyErrorField> errors;

}