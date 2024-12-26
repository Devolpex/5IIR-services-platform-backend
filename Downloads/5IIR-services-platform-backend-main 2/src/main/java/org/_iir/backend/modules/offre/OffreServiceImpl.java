package org._iir.backend.modules.offre;

import java.util.List;
import java.util.stream.Collectors;

import org._iir.backend.exception.OwnNotFoundException;
import org._iir.backend.interfaces.IService;
import org._iir.backend.modules.offre.dto.OffreDTO;
import org._iir.backend.modules.prestataire.Prestataire;
import org._iir.backend.modules.prestataire_services.PrestataireServiceID;
import org._iir.backend.modules.prestataire_services.PrestataireServices;
import org._iir.backend.modules.prestataire_services.PrestataireServicesRepository;
import org._iir.backend.modules.service.ServiceRepository;
import org._iir.backend.modules.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OffreServiceImpl implements IService<Offre, OffreDTO, OffreREQ, OffreREQ, Long> {

    private final OffreRepository offreRepository;
    private final OffreMapperImpl offreMapper;
    private final UserService userService;
    private final PrestataireServicesRepository PSRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public OffreDTO create(OffreREQ req) {
        // Find the authenticated user
        Prestataire prestataire = (Prestataire) userService.getAuthenticatedUser();
        // Check if th service exists
        if (!serviceRepository.existsById(req.serviceId())) {
            log.error("Service not found");
            throw new RuntimeException("Service not found");
        }
        // Build the PrestataireServiceID
        PrestataireServiceID prestataireServiceID = PrestataireServiceID.builder()
                .prestataireId(prestataire.getId())
                .serviceId(req.serviceId())
                .build();
        // Find the PrestataireService
        PrestataireServices prestataireServices = PSRepository
                .findById(prestataireServiceID)
                .orElseThrow(() -> {
                    log.error("Prestataire's service not found");
                    throw new RuntimeException("Prestataire's service not found");
                });
        // Create the Offre
        Offre offre = Offre.builder()
                .description(req.description())
                .tarif(req.tarif())
                .prestataireService(prestataireServices)
                .build();
        // Save the Offre
        offre = offreRepository.save(offre);
        // Return the OffreDTO
        return offreMapper.toDTO(offre);
    }

    @Override
    public OffreDTO update(OffreREQ req, Long id) {
        // Find the Offre
        Offre offre = offreRepository.findById(id).orElseThrow(() -> {
            log.error("Offre not found");
            throw new OwnNotFoundException("Offre not found");
        });
        // Check if the authenticated prestataire is the owner of the Offre
        Prestataire prestataire = (Prestataire) userService.getAuthenticatedUser();
        if (offre.getPrestataireService().getPrestataire().getId() != prestataire.getId()) {
            log.error("The authenticated prestataire is not the owner of the Offre");
            throw new OwnNotFoundException("You are not the owner of the Offre, you can't update it");
        } else {
            // Update the Offre
            offre.setDescription(req.description());
            offre.setTarif(req.tarif());
            // Check if the service updated
            if (!req.serviceId().equals(offre.getPrestataireService().getService().getId())) {
                // Check if th service exists
                if (!serviceRepository.existsById(req.serviceId())) {
                    log.error("Service not found");
                    throw new OwnNotFoundException("Service not found");
                }
                // Build the PrestataireServiceID
                PrestataireServiceID prestataireServiceID = PrestataireServiceID.builder()
                        .prestataireId(prestataire.getId())
                        .serviceId(req.serviceId())
                        .build();
                // Find the PrestataireService
                PrestataireServices prestataireServices = PSRepository
                        .findById(prestataireServiceID)
                        .orElseThrow(() -> {
                            log.error("Prestataire's service not found");
                            throw new OwnNotFoundException("Prestataire's service not found");
                        });
                // Update the PrestataireService
                offre.setPrestataireService(prestataireServices);
            }
            // Save the Offre
            offre = offreRepository.save(offre);
            // Return the OffreDTO
            return offreMapper.toDTO(offre);
        }
    }

    @Override
    public void delete(Long id) {
        // Find the Offre
        Offre offre = offreRepository.findById(id).orElseThrow(() -> {
            log.error("Offre not found");
            throw new OwnNotFoundException("Offre not found");
        });
        // Check if the authenticated prestataire is the owner of the Offre
        Prestataire prestataire = (Prestataire) userService.getAuthenticatedUser();
        if (offre.getPrestataireService().getPrestataire().getId() != prestataire.getId()) {
            log.error("The authenticated prestataire is not the owner of the Offre");
            throw new OwnNotFoundException("You are not the owner of the Offre, you can't delete it");
        } else {
            // Delete the Offre
            offreRepository.delete(offre);
        }
    }

    @Override
    public OffreDTO findById(Long id) {
        return offreRepository.findById(id)
                .map(offreMapper::toDTO)
                .orElseThrow(() -> {
                    log.error("Offre not found");
                    throw new RuntimeException("Offre not found");
                });
    }

    @Override
    public List<OffreDTO> findList() {
        return offreRepository.findAll().stream()
                .map(offreMapper::toDTO)
                .toList();
    }

    @Override
    public Page<OffreDTO> findPage(Pageable pageable) {
        return offreRepository.findAll(pageable)
                .map(offreMapper::toDTO);
    }
    public List<OffreDTO> findOffersByAuthenticatedPrestataire() {
        Prestataire authenticatedPrestataire = (Prestataire) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authenticatedPrestataire == null || authenticatedPrestataire.getId() == null) {
            log.error("Prestataire non authentifié ou ID non trouvé.");
            throw new OwnNotFoundException("Utilisateur non authentifié.");
        }

        return offreRepository.findByPrestataireId(authenticatedPrestataire.getId())
                .stream()
                .map(offreMapper::toDTO)
                .collect(Collectors.toList());
    }

}
