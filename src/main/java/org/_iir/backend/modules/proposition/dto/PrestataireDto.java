package org._iir.backend.modules.proposition.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrestataireDto {
    private Long id;
    private String email;
    
}
