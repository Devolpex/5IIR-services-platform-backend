package org._iir.backend.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IService <E,DTO,CREQ,UREQ,ID> {
    DTO create(CREQ req);
    DTO update(UREQ req,ID id);
    void delete(ID id);
    DTO findById(ID id);
    List<DTO> findList();
    Page<DTO> findPage(Pageable pageable);
}
