// package org._iir.backend.demande_order;

// import org._iir.backend.exception.OwnNotFoundException;
// import org._iir.backend.modules.demande.Demande;
// import org._iir.backend.modules.demandeur.Demandeur;
// import org._iir.backend.modules.order.OrderStatus;
// import org._iir.backend.modules.order.demande.DemandeOrder;
// import org._iir.backend.modules.order.demande.DemandeOrderMapperImpl;
// import org._iir.backend.modules.order.demande.DemandeOrderREQ;
// import org._iir.backend.modules.order.demande.DemandeOrderRepository;
// import org._iir.backend.modules.order.demande.DemandeOrderServiceImpl;
// import org._iir.backend.modules.order.dto.DemandeOrderDTO;
// import org._iir.backend.modules.proposition.Proposition;
// import org._iir.backend.modules.proposition.PropositionDao;
// import org._iir.backend.modules.user.UserService;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.mockito.junit.jupiter.MockitoExtension;

// import java.util.Date;
// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
// class DemandeOrderServiceTest {

//     @InjectMocks
//     private DemandeOrderServiceImpl service; // Replace with your actual service class name

//     @Mock
//     private PropositionDao propositionDao;

//     @Mock
//     private UserService userService;

//     @Mock
//     private DemandeOrderRepository orderRepository;

//     @Mock
//     private DemandeOrderMapperImpl orderMapper;

//     private DemandeOrderREQ mockRequest;
//     private Proposition mockProposition;
//     private Demandeur mockDemandeur;
//     private DemandeOrder mockOrder;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);

//         // Mock Request
//         mockRequest = new DemandeOrderREQ(1L);

//         // Mock Demandeur
//         mockDemandeur = new Demandeur();
//         mockDemandeur.setId(1L);

//         // Mock Proposition
//         Demande mockDemande = new Demande();
//         mockDemande.setDemandeur(mockDemandeur);

//         mockProposition = new Proposition();
//         mockProposition.setId(1L);
//         mockProposition.setDemande(mockDemande);

//         // Mock Order
//         mockOrder = DemandeOrder.builder()
//                 .id(1L)
//                 .proposition(mockProposition)
//                 .status(OrderStatus.NEW)
//                 .orderDate(new Date())
//                 .build();
//     }

//     @Test
//     void create_Success() {
//         // Arrange
//         when(propositionDao.findById(mockRequest.propositionId())).thenReturn(Optional.of(mockProposition));
//         when(userService.getAuthenticatedUser()).thenReturn(mockDemandeur);
//         when(orderRepository.save(any(DemandeOrder.class))).thenReturn(mockOrder);
//         when(orderMapper.toDTO(any(DemandeOrder.class))).thenReturn(new DemandeOrderDTO());

//         // Act
//         DemandeOrderDTO result = service.create(mockRequest);

//         // Assert
//         assertNotNull(result);
//         verify(propositionDao, times(1)).findById(mockRequest.propositionId());
//         verify(userService, times(1)).getAuthenticatedUser();
//         verify(orderRepository, times(1)).save(any(DemandeOrder.class));
//         verify(orderMapper, times(1)).toDTO(any(DemandeOrder.class));
//     }

//     @Test
//     void create_PropositionNotFound() {
//         // Arrange
//         when(propositionDao.findById(mockRequest.propositionId())).thenReturn(Optional.empty());

//         // Act & Assert
//         OwnNotFoundException exception = assertThrows(OwnNotFoundException.class, () -> service.create(mockRequest));
//         assertEquals("Proposition not exists", exception.getMessage());
//         verify(propositionDao, times(1)).findById(mockRequest.propositionId());
//         verify(userService, never()).getAuthenticatedUser();
//         verify(orderRepository, never()).save(any(DemandeOrder.class));
//         verify(orderMapper, never()).toDTO(any(DemandeOrder.class));
//     }

//     @Test
//     void create_DemandeurNotOwner() {
//         // Arrange
//         Demandeur differentDemandeur = new Demandeur();
//         differentDemandeur.setId(2L); // Different ID
//         when(propositionDao.findById(mockRequest.propositionId())).thenReturn(Optional.of(mockProposition));
//         when(userService.getAuthenticatedUser()).thenReturn(differentDemandeur);

//         // Act & Assert
//         OwnNotFoundException exception = assertThrows(OwnNotFoundException.class, () -> service.create(mockRequest));
//         assertEquals("Demandeur is not the owner of demande", exception.getMessage());
//         verify(propositionDao, times(1)).findById(mockRequest.propositionId());
//         verify(userService, times(1)).getAuthenticatedUser();
//         verify(orderRepository, never()).save(any(DemandeOrder.class));
//         verify(orderMapper, never()).toDTO(any(DemandeOrder.class));
//     }
// }
