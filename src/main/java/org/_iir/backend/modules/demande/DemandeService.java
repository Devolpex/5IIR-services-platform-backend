package org._iir.backend.modules.demande;

import java.util.List;
import java.util.stream.Collectors;

import org._iir.backend.exception.OwnNotFoundException;
import org._iir.backend.interfaces.IService;
import org._iir.backend.modules.demande.dto.DemandeDTO;
import org._iir.backend.modules.demande.dto.DemandeREQ;
import org._iir.backend.modules.demandeur.Demandeur;
import org._iir.backend.modules.demandeur.DemandeurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DemandeService implements IService<Demande, DemandeDTO, DemandeREQ, DemandeREQ, Long> {

    // Logger
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    // Repository
    private final DemandeRepository repository;
    private final DemandeurRepository demandeurRepository;

    // Mapper
    private final DemandeMapperImpl mapper;

    @Override
    public DemandeDTO create(DemandeREQ req) {
        // Build the DemandeService Entity from the request
        Demande demandeService = Demande.builder()
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
        // demandeurrepository.findByEmail(user.getEmail()).orElseThrow(() -> {
        // logger.error("Demandeur not found for user : {}", user.getEmail());
        // throw new OwnNotFoundException("Demandeur not found");
        // });

        // Demandeur form the request
        Demandeur demandeur = demandeurRepository.findById(req.demandeurId()).orElseThrow(() -> {
            logger.error("Demandeur not found for id : {}", req.demandeurId());
            throw new OwnNotFoundException("Demandeur not found");
        });
        // Set the Demandeur to the DemandeService
        demandeService.setDemandeur(demandeur);
        // Save the DemandeService
        demandeService = repository.save(demandeService);
        // Send Email to the Demandeur

        // Return the DemandeServiceDTO
        return mapper.toDTO(demandeService);
    }

    @Override
    public DemandeDTO update(DemandeREQ req, Long id) {
        // Find the DemandeService by the id
        return repository.findById(id)
                .map(demande -> {
                    // Update the DemandeService
                    demande.setService(req.service());
                    demande.setDescription(req.description());
                    demande.setLieu(req.lieu());
                    demande.setDateDisponible(req.dateDisponible());
                    // Save the updated DemandeService
                    demande = repository.save(demande);
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
        repository.findById(id)
                .ifPresentOrElse(
                        // Delete the DemandeService
                        repository::delete,
                        // Throw Exception
                        () -> {
                            logger.error("DemandeService not found for id : {}", id);
                            throw new OwnNotFoundException("DemandeService not found");
                        });
    }

    @Override
    public DemandeDTO findById(Long id) {
        // Find and return the DemandeServiceDTO
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> {
                    logger.error("DemandeService not found for id : {}", id);
                    throw new OwnNotFoundException("DemandeService not found");
                });
    }

    @Override
    public List<DemandeDTO> findList() {
        // Find and return the list of DemandeServiceDTO
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DemandeDTO> findPage(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toDTO);
    }
}
