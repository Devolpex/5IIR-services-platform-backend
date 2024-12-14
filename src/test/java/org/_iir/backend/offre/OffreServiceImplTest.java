// package org._iir.backend.offre;

// import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.verifyNoInteractions;
// import static org.mockito.Mockito.when;

// import java.util.List;
// import java.util.Optional;

// import org._iir.backend.exception.OwnNotFoundException;
// import org._iir.backend.modules.offre.Offre;
// import org._iir.backend.modules.offre.OffreMapperImpl;
// import org._iir.backend.modules.offre.OffreREQ;
// import org._iir.backend.modules.offre.OffreRepository;
// import org._iir.backend.modules.offre.OffreServiceImpl;
// import org._iir.backend.modules.offre.dto.OffreDTO;
// import org._iir.backend.modules.prestataire.Prestataire;
// import org._iir.backend.modules.prestataire_services.PrestataireServiceID;
// import org._iir.backend.modules.prestataire_services.PrestataireServices;
// import org._iir.backend.modules.prestataire_services.PrestataireServicesRepository;
// import org._iir.backend.modules.service.Service;
// import org._iir.backend.modules.service.ServiceRepository;
// import org._iir.backend.modules.user.UserService;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageImpl;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;

// public class OffreServiceImplTest {
//         @Mock
//     private UserService userService;

//     @Mock
//     private ServiceRepository serviceRepository;

//     @Mock
//     private PrestataireServicesRepository PSRepository;

//     @Mock
//     private OffreRepository offreRepository;

//     @Mock
//     private OffreMapperImpl offreMapper;

//     @InjectMocks
//     private OffreServiceImpl offreService; // The service being tested

//     private Prestataire mockPrestataire;
//     private OffreREQ mockReq;
//     private PrestataireServiceID mockPrestataireServiceID;
//     private PrestataireServices mockPrestataireServices;
//     private Offre mockOffre;
//     private OffreDTO mockOffreDTO;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);

//         // Mock authenticated user
//         mockPrestataire = new Prestataire();
//         mockPrestataire.setId(1L);

//         // Mock request
//         mockReq = new OffreREQ("Test description", 100.0, 2L);

//         // Mock IDs and entities
//         mockPrestataireServiceID = PrestataireServiceID.builder()
//                 .prestataireId(mockPrestataire.getId())
//                 .serviceId(mockReq.serviceId())
//                 .build();

//         // Service Mock
//         Service service = new Service();
//         service.setId(mockReq.serviceId());

//         mockPrestataireServices = new PrestataireServices();
//         mockPrestataireServices.setId(mockPrestataireServiceID);
//         mockPrestataireServices.setPrestataire(mockPrestataire);
//         mockPrestataireServices.setService(service);


//         mockOffre = Offre.builder()
//                 .description(mockReq.description())
//                 .tarif(mockReq.tarif())
//                 .prestataireService(mockPrestataireServices)
//                 .build();

//         mockOffreDTO = new OffreDTO();
//         mockOffreDTO.setDescription("Test description");
//         mockOffreDTO.setTarif(100.0);
//     }

//     @Test
//     void testCreate_Success() {
//         // Mock behavior for success
//         when(userService.getAuthenticatedUser()).thenReturn(mockPrestataire);
//         when(serviceRepository.existsById(mockReq.serviceId())).thenReturn(true);
//         when(PSRepository.findById(mockPrestataireServiceID)).thenReturn(java.util.Optional.of(mockPrestataireServices));
//         when(offreRepository.save(any(Offre.class))).thenReturn(mockOffre);
//         when(offreMapper.toDTO(mockOffre)).thenReturn(mockOffreDTO);

//         // Call the method
//         OffreDTO result = offreService.create(mockReq);

//         // Verify the interactions and assert results
//         verify(userService).getAuthenticatedUser();
//         verify(serviceRepository).existsById(mockReq.serviceId());
//         verify(PSRepository).findById(mockPrestataireServiceID);
//         verify(offreRepository).save(any(Offre.class));
//         verify(offreMapper).toDTO(mockOffre);

//         assertNotNull(result);
//         assertEquals("Test description", result.getDescription());
//         assertEquals(100.0, result.getTarif());
//     }

//     @Test
//     void testCreate_ServiceNotFound() {
//         // Mock behavior for service not found
//         when(userService.getAuthenticatedUser()).thenReturn(mockPrestataire);
//         when(serviceRepository.existsById(mockReq.serviceId())).thenReturn(false);

//         // Expect exception
//         RuntimeException exception = assertThrows(RuntimeException.class, () -> offreService.create(mockReq));

//         // Verify the interactions and exception message
//         verify(userService).getAuthenticatedUser();
//         verify(serviceRepository).existsById(mockReq.serviceId());
//         assertEquals("Service not found", exception.getMessage());
//     }

//     @Test
//     void testCreate_PrestataireServiceNotFound() {
//         // Mock behavior for Prestataire's service not found
//         when(userService.getAuthenticatedUser()).thenReturn(mockPrestataire);
//         when(serviceRepository.existsById(mockReq.serviceId())).thenReturn(true);
//         when(PSRepository.findById(mockPrestataireServiceID)).thenReturn(java.util.Optional.empty());

//         // Expect exception
//         RuntimeException exception = assertThrows(RuntimeException.class, () -> offreService.create(mockReq));

//         // Verify the interactions and exception message
//         verify(userService).getAuthenticatedUser();
//         verify(serviceRepository).existsById(mockReq.serviceId());
//         verify(PSRepository).findById(mockPrestataireServiceID);
//         assertEquals("Prestataire's service not found", exception.getMessage());
//     }

//         @Test
//     void findById_shouldReturnOffreDTO_whenOffreExists() {
//         // Arrange
//         Long id = 1L;
//         Offre mockEntity = new Offre(); // Replace with your entity class
//         OffreDTO mockDTO = new OffreDTO(); // Replace with your DTO class

//         when(offreRepository.findById(id)).thenReturn(Optional.of(mockEntity));
//         when(offreMapper.toDTO(mockEntity)).thenReturn(mockDTO);

//         // Act
//         OffreDTO result = offreService.findById(id);

//         // Assert
//         assertNotNull(result);
//         assertEquals(mockDTO, result);
//         verify(offreRepository, times(1)).findById(id);
//         verify(offreMapper, times(1)).toDTO(mockEntity);
//     }

//     @Test
//     void findById_shouldThrowException_whenOffreDoesNotExist() {
//         // Arrange
//         Long id = 1L;

//         when(offreRepository.findById(id)).thenReturn(Optional.empty());

//         // Act & Assert
//         RuntimeException exception = assertThrows(RuntimeException.class, () -> offreService.findById(id));
//         assertEquals("Offre not found", exception.getMessage());
//         verify(offreRepository, times(1)).findById(id);
//         verifyNoInteractions(offreMapper);
//     }

//     @Test
//     void findList_shouldReturnListOfOffreDTOs() {
//         // Arrange
//         List<Offre> mockEntities = List.of(new Offre(), new Offre()); // Replace with your entity class
//         List<OffreDTO> mockDTOs = List.of(new OffreDTO(), new OffreDTO()); // Replace with your DTO class

//         when(offreRepository.findAll()).thenReturn(mockEntities);
//         when(offreMapper.toDTO(any(Offre.class))).thenAnswer(invocation -> new OffreDTO());

//         // Act
//         List<OffreDTO> result = offreService.findList();

//         // Assert
//         assertNotNull(result);
//         assertEquals(2, result.size());
//         verify(offreRepository, times(1)).findAll();
//         verify(offreMapper, times(2)).toDTO(any(Offre.class));
//     }

//     @Test
//     void findPage_shouldReturnPageOfOffreDTOs() {
//         // Arrange
//         Pageable pageable = PageRequest.of(0, 2);
//         List<Offre> mockEntities = List.of(new Offre(), new Offre()); // Replace with your entity class
//         Page<Offre> mockPage = new PageImpl<>(mockEntities, pageable, 2);
//         when(offreRepository.findAll(pageable)).thenReturn(mockPage);
//         when(offreMapper.toDTO(any(Offre.class))).thenAnswer(invocation -> new OffreDTO());

//         // Act
//         Page<OffreDTO> result = offreService.findPage(pageable);

//         // Assert
//         assertNotNull(result);
//         assertEquals(2, result.getContent().size());
//         verify(offreRepository, times(1)).findAll(pageable);
//         verify(offreMapper, times(2)).toDTO(any(Offre.class));
//     }


//         @Test
//     void update_shouldUpdateOffreSuccessfully() {
//         // Arrange
//         when(offreRepository.findById(1L)).thenReturn(Optional.of(mockOffre));
//         when(userService.getAuthenticatedUser()).thenReturn(mockPrestataire);
//         when(serviceRepository.existsById(2L)).thenReturn(true);

//         PrestataireServiceID prestataireServiceID = PrestataireServiceID.builder()
//                 .prestataireId(1L)
//                 .serviceId(2L)
//                 .build();

//         PrestataireServices mockPrestataireServices = new PrestataireServices();
//         when(PSRepository.findById(prestataireServiceID)).thenReturn(Optional.of(mockPrestataireServices));

//         when(offreRepository.save(any(Offre.class))).thenReturn(mockOffre);
//         when(offreMapper.toDTO(mockOffre)).thenReturn(new OffreDTO());

//         // Act
//         OffreDTO result = offreService.update(mockReq, 1L);

//         // Assert
//         assertNotNull(result);
//         verify(offreRepository, times(1)).save(any(Offre.class));
//         verify(offreMapper, times(1)).toDTO(mockOffre);
//     }

//     @Test
//     void update_shouldThrowException_whenOffreNotFound() {
//         // Arrange
//         when(offreRepository.findById(1L)).thenReturn(Optional.empty());

//         // Act & Assert
//         RuntimeException exception = assertThrows(RuntimeException.class, () -> offreService.update(mockReq, 1L));
//         assertEquals("Offre not found", exception.getMessage());
//     }

//     @Test
//     void update_shouldThrowException_whenAuthenticatedUserIsNotOwner() {
//         // Arrange
//         when(offreRepository.findById(1L)).thenReturn(Optional.of(mockOffre));

//         Prestataire anotherPrestataire = new Prestataire();
//         anotherPrestataire.setId(2L);
//         when(userService.getAuthenticatedUser()).thenReturn(anotherPrestataire);

//         // Act & Assert
//         RuntimeException exception = assertThrows(RuntimeException.class, () -> offreService.update(mockReq, 1L));
//         assertEquals("You are not the owner of the Offre, you can't update it", exception.getMessage());
//     }

//     @Test
//     void update_shouldThrowException_whenServiceNotFound() {
//         // Arrange
//         when(offreRepository.findById(1L)).thenReturn(Optional.of(mockOffre));
//         when(userService.getAuthenticatedUser()).thenReturn(mockPrestataire);
//         when(serviceRepository.existsById(2L)).thenReturn(false);

//         // Act & Assert
//         OwnNotFoundException exception = assertThrows(OwnNotFoundException.class, () -> offreService.update(mockReq, 1L));
//         assertEquals("Service not found", exception.getMessage());
//     }

//     @Test
//     void delete_shouldDeleteOffreSuccessfully() {
//         // Arrange
//         when(offreRepository.findById(1L)).thenReturn(Optional.of(mockOffre));
//         when(userService.getAuthenticatedUser()).thenReturn(mockPrestataire);

//         // Act
//         assertDoesNotThrow(() -> offreService.delete(1L));

//         // Assert
//         verify(offreRepository, times(1)).delete(mockOffre);
//     }

//     @Test
//     void delete_shouldThrowException_whenOffreNotFound() {
//         // Arrange
//         when(offreRepository.findById(1L)).thenReturn(Optional.empty());

//         // Act & Assert
//         RuntimeException exception = assertThrows(RuntimeException.class, () -> offreService.delete(1L));
//         assertEquals("Offre not found", exception.getMessage());
//     }

//     @Test
//     void delete_shouldThrowException_whenAuthenticatedUserIsNotOwner() {
//         // Arrange
//         when(offreRepository.findById(1L)).thenReturn(Optional.of(mockOffre));

//         Prestataire anotherPrestataire = new Prestataire();
//         anotherPrestataire.setId(2L);
//         when(userService.getAuthenticatedUser()).thenReturn(anotherPrestataire);

//         // Act & Assert
//         RuntimeException exception = assertThrows(RuntimeException.class, () -> offreService.delete(1L));
//         assertEquals("You are not the owner of the Offre, you can't delete it", exception.getMessage());
//     }
// }
