package org._iir.backend.modules.proposition;

import org._iir.backend.interfaces.IMapper;
import org._iir.backend.modules.proposition.dto.DemandeDto;
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
                .demandeDto(DemandeDto.builder()
                        .id(entity.getDemande().getId())
                        .description(entity.getDemande().getDescription())
                        .dateDisponible(entity.getDemande().getDateDisponible())
                        .build())
                .prestataireDto(PrestataireDto.builder()
                        .id(entity.getPrestataire().getId())
                        .email(entity.getPrestataire().getEmail())
                        .build())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
