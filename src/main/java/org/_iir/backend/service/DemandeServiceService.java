package org._iir.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org._iir.backend.bean.DemandeService;
import org._iir.backend.bean.Demandeur;
import org._iir.backend.dao.DemandeServiceDao;
import org._iir.backend.dao.DemandeurDao;
import org._iir.backend.dto.demande_service.DemandeServiceDTO;
import org._iir.backend.exception.OwnNotFoundException;
import org._iir.backend.interfaces.IService;
import org._iir.backend.mapper.DemandeServiceMapperImpl;
import org._iir.backend.request.DemandeServiceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DemandeServiceService
        implements IService<DemandeService, DemandeServiceDTO, DemandeServiceRequest, DemandeServiceRequest, Long> {

    // Logger
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    // DemandeService
    private final DemandeServiceDao dao;
    private final DemandeServiceMapperImpl mapper;

    // Demandeur
    private final DemandeurDao demandeurDao;

    @Override
    public DemandeServiceDTO create(DemandeServiceRequest req) {
        // Build the DemandeService Entity from the request
        DemandeService demandeService = DemandeService.builder()
                .service(req.service())
                .description(req.description())
                .lieu(req.lieu())
                .dateDisponible(req.dateDisponible())
                .build();

        // // Get the User form the SecurityContext
        // User user = (User)
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // // Find the Demandeur by the User
        // Demandeur demandeur =
        // demandeurDao.findByEmail(user.getEmail()).orElseThrow(() -> {
        // logger.error("Demandeur not found for user : {}", user.getEmail());
        // throw new OwnNotFoundException("Demandeur not found");
        // });

        // Demandeur form the request
        Demandeur demandeur = demandeurDao.findById(req.demandeurId()).orElseThrow(() -> {
            logger.error("Demandeur not found for id : {}", req.demandeurId());
            throw new OwnNotFoundException("Demandeur not found");
        });
        // Set the Demandeur to the DemandeService
        demandeService.setDemandeur(demandeur);
        // Save the DemandeService
        demandeService = dao.save(demandeService);
        // Send Email to the Demandeur

        // Return the DemandeServiceDTO
        return mapper.toDTO(demandeService);
    }

    @Override
    public DemandeServiceDTO update(DemandeServiceRequest req, Long id) {
        // Find the DemandeService by the id
        return dao.findById(id)
                .map(demande -> {
                    // Update the DemandeService
                    demande.setService(req.service());
                    demande.setDescription(req.description());
                    demande.setLieu(req.lieu());
                    demande.setDateDisponible(req.dateDisponible());
                    // Save the updated DemandeService
                    demande = dao.save(demande);
                    // Return the updated DemandeServiceDTO
                    return mapper.toDTO(demande);
                })
                .orElseThrow(() -> {
                    logger.error("DemandeService not found for id : {}", id);
                    throw new OwnNotFoundException("DemandeService not found");
                });
    }

    @Override
    public void delete(Long id) {
        dao.findById(id)
                .ifPresentOrElse(
                        // Delete the DemandeService
                        dao::delete,
                        // Throw Exception
                        () -> {
                            logger.error("DemandeService not found for id : {}", id);
                            throw new OwnNotFoundException("DemandeService not found");
                        });
    }

    @Override
    public DemandeServiceDTO findById(Long id) {
        // Find and return the DemandeServiceDTO
        return dao.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> {
                    logger.error("DemandeService not found for id : {}", id);
                    throw new OwnNotFoundException("DemandeService not found");
                });
    }

    @Override
    public List<DemandeServiceDTO> findList() {
        // Find and return the list of DemandeServiceDTO
        return dao.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DemandeServiceDTO> findPage(Pageable pageable) {
        return dao.findAll(pageable)
                .map(mapper::toDTO);
    }
}
