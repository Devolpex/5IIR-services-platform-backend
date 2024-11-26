package org._iir.backend.handler;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OwnErrorResponse {
        private String message;
        private List<OwnError> errors;
}
