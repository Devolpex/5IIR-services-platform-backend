package org._iir.backend.config;

import java.util.Date;
import java.util.List;

import org._iir.backend.modules.demande.Demande;
import org._iir.backend.modules.demande.DemandeRepository;
import org._iir.backend.modules.demandeur.Demandeur;
import org._iir.backend.modules.offre.Offre;
import org._iir.backend.modules.offre.OffreRepository;
import org._iir.backend.modules.order.OrderStatus;
import org._iir.backend.modules.order.demande.DemandeOrder;
import org._iir.backend.modules.order.demande.DemandeOrderRepository;
import org._iir.backend.modules.order.offre.OffreOrderRepository;
import org._iir.backend.modules.order.offre.OrderOffre;
import org._iir.backend.modules.prestataire.Prestataire;
import org._iir.backend.modules.prestataire.PrestataireDao;
import org._iir.backend.modules.prestataire_services.PrestataireServiceID;
import org._iir.backend.modules.prestataire_services.PrestataireServices;
import org._iir.backend.modules.prestataire_services.PrestataireServicesRepository;
import org._iir.backend.modules.proposition.Proposition;
import org._iir.backend.modules.proposition.PropositionDao;
import org._iir.backend.modules.service.Service;
import org._iir.backend.modules.service.ServiceRepository;
import org._iir.backend.modules.user.Role;
import org._iir.backend.modules.user.User;
import org._iir.backend.modules.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.javafaker.Faker;

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
        private final PrestataireDao prestatairerDao;

        private final OffreOrderRepository offreOrderRepository;
        private final DemandeOrderRepository demandeOrderRepository;
        private final Faker faker = new Faker();

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
                                        .accountVerified(true)
                                        .build();
                        userRepository.save(user);

                        // Create Prestataire User
                        Prestataire prestataire = Prestataire
                                        .builder()
                                        .nom("prestataire")
                                        .email("prestataire@gmail.com")
                                        .password(passwordEncoder.encode(PASSWORD))
                                        .role(Role.PRESTATAIRE)
                                        .accountVerified(true)
                                        .build();
                        userRepository.save(prestataire);

                        // Create Demandeur User
                        Demandeur demandeur = Demandeur
                                        .builder()
                                        .nom("demandeur")
                                        .email("demandeur@gmail.com")
                                        .password(passwordEncoder.encode(PASSWORD))
                                        .role(Role.DEMANDEUR)
                                        .accountVerified(true)
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
                                .emailDemandeur(demandeur.getEmail()) // Initialiser le champ emailDemandeur
                                .build();
                        demandeRepository.save(demande1);

                        Demande demande2 = Demande.builder()
                                .service("Plomberie")
                                .description("Demande de réparation plomberie")
                                .dateDisponible(new Date())
                                .lieu("Ariana")
                                .demandeur(demandeur)
                                .emailDemandeur(demandeur.getEmail()) // Initialiser le champ emailDemandeur
                                .build();
                        demandeRepository.save(demande2);

                        Demande demande3 = Demande.builder()
                                .service("Peinture")
                                .description("Demande de peinture")
                                .dateDisponible(new Date())
                                .lieu("Ben Arous")
                                .demandeur(demandeur)
                                .emailDemandeur(demandeur.getEmail()) // Initialiser le champ emailDemandeur
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

                        // Insert 3 services
                        this.insertServices(3);
                        // Insert 5 prestataires
                        this.insertPrestataires(5);
                        // Insert 10 demandes
                        this.insertDemandes(10);
                        this.insertPropositions();
                        // Insert DemandeOrder for Each Demande
                        this.insertDemandeOrder();

                };
        }

        // Method to insert prestataires dynamically
        private void insertPrestataires(int nbr) {
                for (int i = 0; i < nbr; i++) {
                        Prestataire prestataire = Prestataire
                                        .builder()
                                        .nom("prestataire" + i)
                                        .email("prestataire" + i + "@gmail.com")
                                        .password(passwordEncoder.encode(PASSWORD))
                                        .role(Role.PRESTATAIRE)
                                        .accountVerified(true)
                                        .build();
                        userRepository.save(prestataire);
                }
        }

        // Method to insert services dynamically
        private void insertServices(int nbr) {
                for (int i = 0; i < nbr; i++) {
                        // Use faker to generate a service title and description
                        Service service = Service.builder()
                                        .title(faker.job().title()) // Generate a job title (can represent a service
                                                                    // like "Plumber" or "Gardener")
                                        .description(faker.lorem().sentence()) // Generate a sentence as description
                                        .build();

                        // Save the service to the repository
                        serviceRepository.save(service);
                }
        }

        // Method to insert demandes dynamically
        private void insertDemandes(int nbr) {
                Demandeur demandeur = demandeRepository.findById(1L).get().getDemandeur();
                for (int i = 0; i < nbr; i++) {
                        Demande demande = Demande.builder()
                                        .service(faker.job().title())
                                        .description(faker.lorem().sentence())
                                        .dateDisponible(faker.date().birthday())
                                        .lieu(faker.address().city())
                                        .demandeur(demandeur)
                                        .build();
                        demandeRepository.save(demande);
                }
        }

        // Method to insert propositions dynamically for each Demande and each
        // Prestataire
        private void insertPropositions() {
                List<Demande> demandes = demandeRepository.findAll();
                List<Prestataire> prestataires = prestatairerDao.findAll();

                for (Demande demande : demandes) {
                        // For each Demande, insert a Proposition for each Prestataire
                        for (Prestataire prestataire : prestataires) {
                                // Create a new Proposition for the current Demande and Prestataire
                                Proposition proposition = Proposition.builder()
                                                .description(faker.lorem().sentence())
                                                .tarifProposer(faker.number().randomDouble(2, 50, 500))
                                                .disponibiliteProposer(faker.date().birthday())
                                                .demande(demande) // Associate the current Demande
                                                .prestataire(prestataire) // Associate the current Prestataire
                                                .build();

                                // Save the created Proposition
                                propositionRepository.save(proposition);
                        }
                }
        }

        // Insert DemandeOrder for Each Demande
        private void insertDemandeOrder() {
                List<Demande> demandes = demandeRepository.findAll();

                for (Demande demande : demandes) {
                        List<Proposition> propositions = demande.getPropositions();

                        // Check if there are propositions available
                        if (!propositions.isEmpty()) {
                                // Get a random proposition
                                int randomIndex = faker.number().numberBetween(0, propositions.size());
                                Proposition proposition = propositions.get(randomIndex);

                                // Create and save DemandeOrder for this Demande
                                DemandeOrder demandeOrder = DemandeOrder.builder()
                                                .proposition(proposition)
                                                .status(OrderStatus.NEW)
                                                .orderDate(new Date())
                                                .build();

                                // Save the DemandeOrder
                                demandeOrderRepository.save(demandeOrder);
                        } else {
                                // Optionally handle the case where there are no propositions
                                System.out.println("No propositions available for Demande ID: " + demande.getId());
                        }
                }
        }

}
