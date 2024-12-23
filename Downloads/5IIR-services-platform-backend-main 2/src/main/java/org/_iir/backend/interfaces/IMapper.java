
package org._iir.backend.interfaces;

public interface IMapper<E, DTO> {

    E toEntity(DTO dto);
    DTO toDTO(E entity);
}