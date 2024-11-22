package org._iir.backend.mapper;

import org._iir.backend.bean.Offre;
import org._iir.backend.dto.OffreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OffreMapper {

    @Mapping(target = "prestataireId", source = "prestataire.id")
    OffreDTO toDTO(Offre offre);

    @Mapping(target = "prestataire", ignore = true)
    Offre toEntity(OffreDTO dto);
}
