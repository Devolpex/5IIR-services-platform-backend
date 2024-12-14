package org._iir.backend.modules.offre.dto;

import lombok.Builder;

@Builder
public record ServiceDTO(
    Long id,
    String title
) {
    
}
