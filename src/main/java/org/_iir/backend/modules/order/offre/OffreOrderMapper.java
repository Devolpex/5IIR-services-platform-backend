package org._iir.backend.modules.order.offre;

import org._iir.backend.interfaces.IMapper;
import org._iir.backend.modules.order.dto.OffreDTO;
import org._iir.backend.modules.order.dto.OffreOrderDTO;
import org._iir.backend.modules.order.dto.ServiceDTO;
import org._iir.backend.modules.order.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class OffreOrderMapper implements IMapper<OrderOffre, OffreOrderDTO> {

    @Override
    public OrderOffre toEntity(OffreOrderDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
    }

    @Override
    public OffreOrderDTO toDTO(OrderOffre entity) {
        // Service
        ServiceDTO serviceDTO = ServiceDTO.builder()
                .id(entity.getOffre().getPrestataireService().getService().getId())
                .title(entity.getOffre().getPrestataireService().getService().getTitle())
                .build();
        // Offre
        OffreDTO offreOrderDTO = OffreDTO.builder()
                .id(entity.getId())
                .service(serviceDTO)
                .description(entity.getOffre().getDescription())
                .tarif(entity.getOffre().getTarif())
                .build();
        // Demandeur
        UserDTO demandeur = UserDTO.builder()
                .id(entity.getDemandeur().getId())
                .nom(entity.getDemandeur().getNom())
                .email(entity.getDemandeur().getEmail())
                .build();
        // Prestataire
        UserDTO prestataire = UserDTO.builder()
                .id(entity.getOffre().getPrestataireService().getPrestataire().getId())
                .nom(entity.getOffre().getPrestataireService().getPrestataire().getNom())
                .email(entity.getOffre().getPrestataireService().getPrestataire().getEmail())
                .build();

        return OffreOrderDTO.builder()
                .id(entity.getId())
                .orderDate(entity.getOrderDate())
                .status(entity.getStatus())
                .offre(offreOrderDTO)
                .demandeur(demandeur)
                .prestataire(prestataire)
                .build();

    }

}
