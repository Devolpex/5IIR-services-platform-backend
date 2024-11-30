package org._iir.backend.config;

import java.util.Date;
import java.util.List;

import org._iir.backend.modules.demande.Demande;
import org._iir.backend.modules.demande.DemandeRepository;
import org._iir.backend.modules.demandeur.Demandeur;
import org._iir.backend.modules.offre.Offre;
import org._iir.backend.modules.offre.OffreRepository;
import org._iir.backend.modules.order.OrderStatus;
import org._iir.backend.modules.order.offre.OffreOrderRepository;
import org._iir.backend.modules.order.offre.OrderOffre;
import org._iir.backend.modules.prestataire.Prestataire;
import org._iir.backend.modules.service.Service;
import org._iir.backend.modules.prestataire_services.PrestataireServices;
import org._iir.backend.modules.prestataire_services.PrestataireServicesRepository;
import org._iir.backend.modules.prestataire_services.PrestataireServiceID;
import org._iir.backend.modules.proposition.Proposition;
import org._iir.backend.modules.proposition.PropositionDao;
import org._iir.backend.modules.user.Role;
import org._iir.backend.modules.user.User;
import org._iir.backend.modules.user.UserRepository;
import org._iir.backend.modules.service.ServiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DatabaseInit {

        private final UserRepository userRepository;
        private final ServiceRepository serviceRepository;
        private final DemandeRepository demandeRepository;
        private final PropositionDao propositionRepository;
        private final PrestataireServicesRepository prestataireServicesRepository;
        private final PasswordEncoder passwordEncoder;
        private final OffreRepository offreRepository;

        private final OffreOrderRepository offreOrderRepository;

        private static final String PASSWORD = "Password";

        @Bean
        CommandLineRunner initDatabase() {

                return args -> {
                        // Create Admin User
                        User user = User.builder()
                                        .nom("admin")
                                        .email("admin@gmail.com")
                                        .password(passwordEncoder.encode(PASSWORD))
                                        .role(Role.ADMIN)
                                        .build();
                        userRepository.save(user);

                        // Create Prestataire User
                        Prestataire prestataire = Prestataire
                                        .builder()
                                        .nom("prestataire")
                                        .email("prestataire@gmail.com")
                                        .password(passwordEncoder.encode(PASSWORD))
                                        .role(Role.PRESTATAIRE)
                                        .build();
                        userRepository.save(prestataire);

                        // Create Demandeur User
                        Demandeur demandeur = Demandeur
                                        .builder()
                                        .nom("demandeur")
                                        .email("demandeur@gmail.com")
                                        .password(passwordEncoder.encode(PASSWORD))
                                        .role(Role.DEMANDEUR)
                                        .build();
                        userRepository.save(demandeur);

                        // Create Services
                        Service service1 = Service.builder()
                                        .title("Electricite")
                                        .description("Service d'electricite")
                                        .build();

                        Service service2 = Service.builder()
                                        .title("Plomberie")
                                        .description("Service de plomberie")
                                        .build();

                        Service service3 = Service.builder()
                                        .title("Peinture")
                                        .description("Service de peinture")
                                        .build();

                        serviceRepository.save(service1);
                        serviceRepository.save(service2);
                        serviceRepository.save(service3);

                        // Create PrestataireServices linking Prestataire and Service
                        PrestataireServiceID prestataireServiceID1 = new PrestataireServiceID(prestataire.getId(),
                                        service1.getId());
                        PrestataireServiceID prestataireServiceID2 = new PrestataireServiceID(prestataire.getId(),
                                        service2.getId());

                        PrestataireServices prestataireServices1 = PrestataireServices.builder()
                                        .id(prestataireServiceID1)
                                        .prestataire(prestataire)
                                        .service(service1)
                                        .build();

                        PrestataireServices prestataireServices2 = PrestataireServices.builder()
                                        .id(prestataireServiceID2)
                                        .prestataire(prestataire)
                                        .service(service2)
                                        .build();

                        prestataireServicesRepository.save(prestataireServices1);
                        prestataireServicesRepository.save(prestataireServices2);

                        // Create two Offres for each PrestataireService
                        Offre offre1 = Offre.builder()
                                        .description("Offre de dépannage électrique")
                                        .tarif(150.0)
                                        .prestataireService(prestataireServices1)
                                        .build();

                        Offre offre2 = Offre.builder()
                                        .description("Offre d'installation électrique")
                                        .tarif(200.0)
                                        .prestataireService(prestataireServices1)
                                        .build();

                        Offre offre3 = Offre.builder()
                                        .description("Offre de réparation plomberie")
                                        .tarif(100.0)
                                        .prestataireService(prestataireServices2)
                                        .build();

                        Offre offre4 = Offre.builder()
                                        .description("Offre d'entretien plomberie")
                                        .tarif(120.0)
                                        .prestataireService(prestataireServices2)
                                        .build();

                        // Save Offres to the database
                        offreRepository.saveAll(List.of(offre1, offre2, offre3, offre4));

                        // Create 10 orders for each offer
                        List<Offre> offres = List.of(offre1, offre2, offre3, offre4);
                        for (Offre offre : offres) {
                                for (int i = 1; i <= 10; i++) {
                                        OrderOffre order = OrderOffre.builder()
                                                        .offre(offre)
                                                        .demandeur(demandeur)
                                                        .orderDate(new Date())
                                                        .status(OrderStatus.NEW)
                                                        .build();
                                        offreOrderRepository.save(order);
                                }
                        }

                        // Create Demande
                        Demande demande1 = Demande.builder()
                                        .service("Electricite")
                                        .description("Demande de dépannage électrique")
                                        .dateDisponible(new Date())
                                        .lieu("Tunis")
                                        .demandeur(demandeur)
                                        .build();
                        demandeRepository.save(demande1);

                        Demande demande2 = Demande.builder()
                                        .service("Plomberie")
                                        .description("Demande de réparation plomberie")
                                        .dateDisponible(new Date())
                                        .lieu("Ariana")
                                        .demandeur(demandeur)
                                        .build();
                        demandeRepository.save(demande2);

                        Demande demande3 = Demande.builder()
                                        .service("Peinture")
                                        .description("Demande de peinture")
                                        .dateDisponible(new Date())
                                        .lieu("Ben Arous")
                                        .demandeur(demandeur)
                                        .build();
                        demandeRepository.save(demande3);

                        // Create Propositions
                        Proposition proposition1 = Proposition.builder()
                                        .description("Proposition de dépannage électrique")
                                        .tarifProposer(150.0)
                                        .disponibiliteProposer(new Date())
                                        .demande(demande1)
                                        .prestataire(prestataire)
                                        .build();
                        propositionRepository.save(proposition1);

                        Proposition proposition2 = Proposition.builder()
                                        .description("Proposition de réparation plomberie")
                                        .tarifProposer(100.0)
                                        .disponibiliteProposer(new Date())
                                        .demande(demande2)
                                        .prestataire(prestataire)
                                        .build();
                        propositionRepository.save(proposition2);

                        Proposition proposition3 = Proposition.builder()
                                        .description("Proposition de peinture")
                                        .tarifProposer(200.0)
                                        .disponibiliteProposer(new Date())
                                        .demande(demande3)
                                        .prestataire(prestataire)
                                        .build();
                        propositionRepository.save(proposition3);
                };
        }
}
