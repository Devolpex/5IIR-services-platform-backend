package org._iir.backend.modules.offre;

import org._iir.backend.interfaces.IMapper;
import org._iir.backend.modules.offre.dto.OffreDTO;
import org._iir.backend.modules.offre.dto.ServiceDTO;
import org._iir.backend.modules.order.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class OffreMapperImpl implements IMapper<Offre, OffreDTO> {

    @Override
    public Offre toEntity(OffreDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
    }

    @Override
    public OffreDTO toDTO(Offre entity) {
        UserDTO prestataire = UserDTO.builder()
                .id(entity.getPrestataireService().getPrestataire().getId())
                .nom(entity.getPrestataireService().getPrestataire().getNom())
                .email(entity.getPrestataireService().getPrestataire().getEmail())
                .build();
        ServiceDTO service = ServiceDTO.builder()
                .id(entity.getPrestataireService().getService().getId())
                .title(entity.getPrestataireService().getService().getTitle())
                .build();

        return OffreDTO.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .tarif(entity.getTarif())
                .prestataire(prestataire)
                .service(service)
                .build();
    }

}
