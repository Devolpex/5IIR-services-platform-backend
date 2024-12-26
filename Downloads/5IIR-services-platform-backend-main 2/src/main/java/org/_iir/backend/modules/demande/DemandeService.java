package org._iir.backend.modules.demande;

import java.util.List;
import java.util.stream.Collectors;

import org._iir.backend.exception.OwnNotFoundException;
import org._iir.backend.interfaces.IService;
import org._iir.backend.modules.demande.dto.DemandeDTO;
import org._iir.backend.modules.demande.dto.DemandeREQ;
import org._iir.backend.modules.demandeur.Demandeur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DemandeService implements IService<Demande, DemandeDTO, DemandeREQ, DemandeREQ, Long> {

    // Logger
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    // Repository
    private final DemandeRepository repository;

    // Mapper
    private final DemandeMapperImpl mapper;

    @Override
    public DemandeDTO create(DemandeREQ req) {
        // Récupérer l'utilisateur connecté depuis le SecurityContext
        Demandeur authenticatedDemandeur = (Demandeur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Créer l'entité Demande
        Demande demande = Demande.builder()
                .service(req.service())
                .description(req.description())
                .lieu(req.lieu())
                .dateDisponible(req.dateDisponible())
                .demandeur(authenticatedDemandeur) // Associer la demande au demandeur connecté
                .emailDemandeur(authenticatedDemandeur.getEmail()) // Associer l'email du demandeur

                .build();

        // Sauvegarder la demande
        Demande savedDemande = repository.save(demande);

        // Retourner le DTO
        return mapper.toDTO(savedDemande);
    }

    @Override
    public DemandeDTO update(DemandeREQ req, Long id) {
        // Trouver la demande par ID
        return repository.findById(id)
                .map(demande -> {
                    // Mettre à jour les champs de la demande
                    demande.setService(req.service());
                    demande.setDescription(req.description());
                    demande.setLieu(req.lieu());
                    demande.setDateDisponible(req.dateDisponible());
                    // Sauvegarder les modifications
                    Demande updatedDemande = repository.save(demande);
                    // Retourner le DTO
                    return mapper.toDTO(updatedDemande);
                })
                .orElseThrow(() -> {
                    logger.error("Demande introuvable pour l'ID : {}", id);
                    throw new OwnNotFoundException("Demande introuvable.");
                });
    }

    @Override
    public void delete(Long id) {
        repository.findById(id)
                .ifPresentOrElse(
                        repository::delete,
                        () -> {
                            logger.error("Demande introuvable pour l'ID : {}", id);
                            throw new OwnNotFoundException("Demande introuvable.");
                        });
    }

    @Override
    public DemandeDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> {
                    logger.error("Demande introuvable pour l'ID : {}", id);
                    throw new OwnNotFoundException("Demande introuvable.");
                });
    }

    @Override
    public List<DemandeDTO> findList() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DemandeDTO> findPage(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toDTO);
    }
    public List<DemandeDTO> findDemandesByAuthenticatedDemandeur() {
        Demandeur authenticatedDemandeur = (Demandeur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authenticatedDemandeur == null || authenticatedDemandeur.getId() == null) {
            logger.error("Demandeur non authentifié ou ID non trouvé.");
            throw new OwnNotFoundException("Utilisateur non authentifié.");
        }

        return repository.findByDemandeurId(authenticatedDemandeur.getId())
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

}
