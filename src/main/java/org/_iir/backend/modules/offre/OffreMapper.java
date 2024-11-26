package org._iir.backend.modules.offre;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OffreMapper {

    @Mapping(source = "prestataire.id", target = "prestataireId")
    OffreDTO toDTO(Offre offre);

    @Mapping(target = "prestataire", ignore = true)
    Offre toEntity(OffreDTO dto);
}
