package org._iir.backend.modules.demande;

import org._iir.backend.interfaces.IMapper;
import org._iir.backend.modules.demande.dto.DemandeDTO;
import org._iir.backend.modules.demande.dto.DemandeurDTO;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DemandeMapperImpl implements IMapper<Demande, DemandeDTO> {

    @Override
    public Demande toEntity(DemandeDTO dto) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
    }

    @Override
    public DemandeDTO toDTO(Demande entity) {
        return DemandeDTO.builder()
                .id(entity.getId())
                .service(entity.getService())
                .description(entity.getDescription())
                .dateDisponible(entity.getDateDisponible())
                .lieu(entity.getLieu())
                .emailDemandeur(entity.getDemandeur().getEmail())
                .demandeur(DemandeurDTO.builder()
                        .id(entity.getDemandeur().getId())
                        .name(entity.getDemandeur().getNom())
                        .build())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}
