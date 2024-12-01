package org._iir.backend.modules.order.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemandeDTO {

    private Long id;
    private String service;
    private String description;
    private String lieu;
    private Date dateDisponible;
}
