package org._iir.backend.modules.order.dto;

import java.util.Date;

import org._iir.backend.modules.order.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OffreOrderDTO {

    private Long id;
    private Date orderDate;
    private OrderStatus status;
    private OffreDTO offre;
    private UserDTO demandeur;
    private UserDTO prestataire;
}
