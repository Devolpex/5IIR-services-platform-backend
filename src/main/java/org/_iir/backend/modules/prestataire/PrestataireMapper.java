package org._iir.backend.modules.prestataire;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PrestataireMapper {

    PrestataireDTO toDTO(Prestataire prestataire);

    Prestataire toEntity(PrestataireDTO dto);
}
