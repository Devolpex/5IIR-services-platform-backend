package org._iir.backend.modules.proposition;

import org._iir.backend.interfaces.IMapper;
import org._iir.backend.modules.proposition.dto.DemandeDto;
import org._iir.backend.modules.proposition.dto.DemandeurDto;
import org._iir.backend.modules.proposition.dto.PrestataireDto;
import org._iir.backend.modules.proposition.dto.PropositionDto;

import org.springframework.stereotype.Component;

@Component
public class PropositionMapper implements IMapper<Proposition, PropositionDto> {
    
    @Override
    public Proposition toEntity(PropositionDto dto) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
    }

    @Override
    public PropositionDto toDTO(Proposition entity) {
        return PropositionDto.builder()
                .id(entity.getId())
                .description(entity.getDescription())   
                .tarifProposer(entity.getTarifProposer())
                .dateDisponible(entity.getDisponibiliteProposer())
                .demande(DemandeDto.builder()
                        .id(entity.getDemande().getId())
                        .service(entity.getDemande().getService())
                        .description(entity.getDemande().getDescription())
                        .dateDisponible(entity.getDemande().getDateDisponible())
                        .lieu(entity.getDemande().getLieu())
                        .demandeur(DemandeurDto.builder()
                                .id(entity.getDemande().getDemandeur().getId())
                                .nom(entity.getDemande().getDemandeur().getNom())
                                .build())
                        .createdAt(entity.getDemande().getCreatedAt())
                        .updatedAt(entity.getDemande().getUpdatedAt())
                        .build())
                .prestataire(PrestataireDto.builder()
                        .id(entity.getPrestataire().getId())
                        .email(entity.getPrestataire().getEmail())
                        .build())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
