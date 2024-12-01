package org._iir.backend.modules.order.demande;

import org._iir.backend.interfaces.IMapper;
import org._iir.backend.modules.order.dto.DemandeDTO;
import org._iir.backend.modules.order.dto.DemandeOrderDTO;
import org._iir.backend.modules.order.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class DemandeOrderMapperImpl implements IMapper<DemandeOrder,DemandeOrderDTO> {

    @Override
    public DemandeOrder toEntity(DemandeOrderDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
    }

    @Override
    public DemandeOrderDTO toDTO(DemandeOrder entity) {
        // DemandeDTO
        DemandeDTO demande = DemandeDTO.builder()
                .id(entity.getProposition().getDemande().getId())
                .service(entity.getProposition().getDemande().getService())
                .description(entity.getProposition().getDemande().getDescription())
                .lieu(entity.getProposition().getDemande().getLieu())
                .dateDisponible(entity.getProposition().getDemande().getDateDisponible())
                .build();
        
        // Demandeur UserDTO
        UserDTO demandeur = UserDTO.builder()
                .id(entity.getProposition().getDemande().getDemandeur().getId())
                .nom(entity.getProposition().getDemande().getDemandeur().getNom())
                .email(entity.getProposition().getDemande().getDemandeur().getEmail())
                .build();
        
        // Prestataire UserDTO
        UserDTO prestataire = UserDTO.builder()
                .id(entity.getProposition().getPrestataire().getId())
                .nom(entity.getProposition().getPrestataire().getNom())
                .email(entity.getProposition().getPrestataire().getEmail())
                .build();

        return DemandeOrderDTO.builder()
                .id(entity.getId())
                .orderDate(entity.getOrderDate())
                .status(entity.getStatus())
                .demande(demande)
                .demandeur(demandeur)
                .prestataire(prestataire)
                .build();
    }
    
}
