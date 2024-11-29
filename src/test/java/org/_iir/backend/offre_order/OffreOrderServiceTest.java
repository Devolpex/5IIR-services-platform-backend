// package org._iir.backend.offre_order;

// import org._iir.backend.exception.OwnNotFoundException;
// import org._iir.backend.modules.demandeur.Demandeur;
// import org._iir.backend.modules.offre.Offre;
// import org._iir.backend.modules.offre.OffreRepository;
// import org._iir.backend.modules.order.OrderStatus;
// import org._iir.backend.modules.order.dto.OffreOrderDTO;
// import org._iir.backend.modules.order.offre.OffreOrderMapper;
// import org._iir.backend.modules.order.offre.OffreOrderREQ;
// import org._iir.backend.modules.order.offre.OffreOrderRepository;
// import org._iir.backend.modules.order.offre.OffreOrderServiceImpl;
// import org._iir.backend.modules.order.offre.OrderOffre;
// import org._iir.backend.modules.user.UserService;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.*;
// import org.springframework.boot.test.context.SpringBootTest;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;
// import static org.junit.jupiter.api.Assertions.*;

// import java.util.Date;
// import java.util.Optional;

// @SpringBootTest
// public class OffreOrderServiceTest {

//     @Mock
//     private OffreRepository offreRepository;

//     @Mock
//     private UserService userService;

//     @Mock
//     private OffreOrderRepository orderRepository;

//     @Mock
//     private OffreOrderMapper orderMapper;

//     @InjectMocks
//     private OffreOrderServiceImpl offreOrderService; 

//     private OffreOrderREQ req;
//     private Offre offre;
//     private Demandeur demandeur;
//     private OrderOffre order;
//     private OffreOrderDTO orderDTO;

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.openMocks(this);

//         // Setup mock data
//         req = new OffreOrderREQ(1L); // Example offreId
//         offre = Offre.builder().id(1L).build();
//         demandeur = Demandeur.builder().id(1L).email("marrouane.dbibih@gmail.com").build();
//         order = OrderOffre.builder()
//                 .id(1L)
//                 .offre(offre)
//                 .demandeur(demandeur)
//                 .status(OrderStatus.NEW).build();
//                 // Create mock DTO object
//         orderDTO = OffreOrderDTO.builder()
//                 .id(1L)
//                 .orderDate(new Date())
//                 .status(OrderStatus.NEW)
//                 .build();

//         // Mock method calls
//         when(offreRepository.findById(1L)).thenReturn(Optional.of(offre));
//         when(userService.getAuthenticatedUser()).thenReturn(demandeur);
//         when(orderRepository.save(any(OrderOffre.class))).thenReturn(order);
//         when(orderMapper.toDTO(any(OrderOffre.class))).thenReturn(orderDTO);
//     }

//     @Test
//     public void testCreateOrderSuccessfully() {
//         // Act
//         OffreOrderDTO result = offreOrderService.create(req);

//         // Assert
//         assertNotNull(result, "The returned order DTO should not be null.");
//         assertEquals(1L, result.getId(), "The order ID should be 1.");
//         assertEquals(OrderStatus.NEW, result.getStatus(), "The order status should be 'NEW'.");

//         // Verify interactions with mocks
//         verify(offreRepository, times(1)).findById(1L);
//         verify(userService, times(1)).getAuthenticatedUser();
//         verify(orderRepository, times(1)).save(any(OrderOffre.class));
//         verify(orderMapper, times(1)).toDTO(any(OrderOffre.class));
//     }

//     @Test
//     public void testCreateOrder_OffreNotFound() {
//         // Arrange: simulate Offre not found
//         when(offreRepository.findById(1L)).thenReturn(Optional.empty());

//         // Act & Assert
//         OwnNotFoundException exception = assertThrows(OwnNotFoundException.class, () -> {
//             offreOrderService.create(req);
//         });
//         assertEquals("Offre not found", exception.getMessage(), "The error message should be 'Offre not found'.");
//     }
// }
