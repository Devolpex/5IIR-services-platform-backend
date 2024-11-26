package org._iir.backend.mapper;

import org._iir.backend.bean.Prestataire;
import org._iir.backend.dto.PrestataireDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PrestataireMapper {

    PrestataireDTO toDTO(Prestataire prestataire);

    Prestataire toEntity(PrestataireDTO dto);
}
