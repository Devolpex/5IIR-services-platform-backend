package org._iir.backend.mapper;

import org._iir.backend.bean.DemandeService;
import org._iir.backend.dto.demande_service.DemandeServiceDTO;
import org._iir.backend.dto.demande_service.DemandeurDTO;
import org._iir.backend.interfaces.IMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DemandeServiceMapperImpl implements IMapper<DemandeService, DemandeServiceDTO> {

    @Override
    public DemandeService toEntity(DemandeServiceDTO dto) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
    }

    @Override
    public DemandeServiceDTO toDTO(DemandeService entity) {
        return DemandeServiceDTO.builder()
                .service(entity.getService())
                .description(entity.getDescription())
                .dateDisponible(entity.getDateDisponible())
                .demandeur(DemandeurDTO.builder()
                        .id(entity.getDemandeur().getId())
                        .name(entity.getDemandeur().getNom())
                        .build())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}
