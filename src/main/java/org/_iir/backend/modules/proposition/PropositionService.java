package org._iir.backend.modules.proposition;

import java.util.List;

import org._iir.backend.exception.OwnNotFoundException;
import org._iir.backend.interfaces.IService;
import org._iir.backend.modules.demande.Demande;
import org._iir.backend.modules.demande.DemandeRepository;
import org._iir.backend.modules.prestataire.Prestataire;
import org._iir.backend.modules.proposition.dto.PropositionDto;
import org._iir.backend.modules.proposition.dto.PropositionReq;
import org._iir.backend.modules.user.UserService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PropositionService implements IService<Proposition, PropositionDto, PropositionReq, PropositionReq, Long> {
    //Logger
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    
    private final PropositionDao repository;
    private final PropositionMapper mapper;
    private final UserService userService;
    private final DemandeRepository demandeRepository;
    

    public PropositionDto create(PropositionReq request) {
        Demande demande = demandeRepository.findById(request.demandeId())
            .orElseThrow(() ->{
                log.error("Demande with id {} not found",request.demandeId());
                return new OwnNotFoundException("Demande not exists");
            });
        
        Prestataire prestataire = (Prestataire) userService.getAuthenticatedUser();


        Proposition proposition = Proposition.builder()
            .tarifProposer(request.tarifProposer())
            .description(request.description())
            .disponibiliteProposer(request.dateDisponibilite())
            .demande(demande)
            .prestataire(prestataire)
            .build();

        return mapper.toDTO(repository.save(proposition));
    }

    @Override
    public PropositionDto update(PropositionReq req, Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Long id) {
        repository.findById(id)
        .ifPresentOrElse(
                // Delete the PropositionService
                repository::delete,
                // Throw Exception
                () -> {
                    logger.error("PropositionService not found for id : {}", id);
                    throw new OwnNotFoundException("PropositionService not found");
                });
    }

    @Override
    public PropositionDto findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PropositionDto> findList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Page<PropositionDto> findPage(Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
