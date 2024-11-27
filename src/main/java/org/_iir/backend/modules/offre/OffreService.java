// package org._iir.backend.modules.offre;

// import org._iir.backend.exception.ResourceNotFoundException;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.support.TransactionTemplate;

// import java.util.List;
// import java.util.stream.Collectors;

// @Service
// public class OffreService {

//     @Autowired
//     private OffreDao offreDao;

//     @Autowired
//     private OffreMapper offreMapper;

//     @Autowired
//     private TransactionTemplate transactionTemplate;

//     /**
//      * Ajouter une nouvelle offre.
//      */
//     public OffreDTO ajouterOffre(OffreDTO offreDTO) {
//         Offre offre = offreMapper.toEntity(offreDTO);
//         validateOffre(offre);
//         return offreMapper.toDTO(offreDao.save(offre));
//     }

//     /**
//      * Mettre à jour une offre existante.
//      */
//     public OffreDTO updateOffre(int id, OffreDTO offreDTO) {
//         return transactionTemplate.execute(status -> {
//             Offre existingOffre = offreDao.findById(id)
//                     .orElseThrow(() -> new ResourceNotFoundException("Offre introuvable avec id : " + id));
//             existingOffre.setDescription(offreDTO.getDescription());
//             existingOffre.setTarif(offreDTO.getTarif());
//             // existingOffre.setDisponibilite(offreDTO.getDisponibilite());
//             return offreMapper.toDTO(offreDao.save(existingOffre));
//         });
//     }

//     /**
//      * Récupérer toutes les offres.
//      */
//     public List<OffreDTO> findAllOffres() {
//         return offreDao.findAll()
//                 .stream()
//                 .map(offreMapper::toDTO)
//                 .collect(Collectors.toList());
//     }

//     /**
//      * Supprimer une offre.
//      */
//     public void deleteOffre(int id) {
//         if (!offreDao.existsById(id)) {
//             throw new ResourceNotFoundException("Offre introuvable avec id : " + id);
//         }
//         offreDao.deleteById(id);
//     }

//     /**
//      * Validation des données.
//      */
//     private void validateOffre(Offre offre) {
//         if (offre.getDescription() == null || offre.getDescription().trim().isEmpty()) {
//             throw new IllegalArgumentException("La description est obligatoire.");
//         }
//         if (offre.getTarif() <= 0) {
//             throw new IllegalArgumentException("Le tarif doit être supérieur à 0.");
//         }
//         // if (offre.getDisponibilite() == null || offre.getDisponibilite().trim().isEmpty()) {
//         //     throw new IllegalArgumentException("La disponibilité est obligatoire.");
//         // }
//     }
// }
