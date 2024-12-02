package org._iir.backend.offre_order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Date;
import java.util.List;

import org._iir.backend.exception.OwnNotFoundException;
import org._iir.backend.modules.demandeur.Demandeur;
import org._iir.backend.modules.offre.Offre;
import org._iir.backend.modules.offre.OffreRepository;
import org._iir.backend.modules.order.OrderStatus;
import org._iir.backend.modules.order.dto.OffreOrderDTO;
import org._iir.backend.modules.order.offre.OffreOrderMapper;
import org._iir.backend.modules.order.offre.OffreOrderREQ;
import org._iir.backend.modules.order.offre.OffreOrderRepository;
import org._iir.backend.modules.order.offre.OffreOrderServiceImpl;
import org._iir.backend.modules.order.offre.OrderOffre;
import org._iir.backend.modules.prestataire.Prestataire;
import org._iir.backend.modules.prestataire.PrestataireService;
import org._iir.backend.modules.prestataire_services.PrestataireServices;
import org._iir.backend.modules.user.User;
import org._iir.backend.modules.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

class OffreOrderServiceImplTest {

    @InjectMocks
    private OffreOrderServiceImpl service;

    @Mock
    private OffreOrderRepository orderRepository;

    @Mock
    private OffreRepository offreRepository;

    @Mock
    private UserService userService;

    @Mock
    private OffreOrderMapper orderMapper;

    private Offre offre;
    private Demandeur demandeur;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Create Offre Order object
        offre = Offre.builder()
                .id(1L)
                .description("Offre description")
                .tarif(100.00)
                .build();
        // Demandeur
        demandeur = Demandeur.builder()
                .id(1L)
                .nom("Demandeur")
                .email("demandeur@gmail.com")
                .build();

    }

    @Test
    public void CreateOrder_Success() {
        System.out.println("Running CreateOrder_Success test...");

        // Arrange
        Long offreId = 1L;
        OffreOrderREQ request = new OffreOrderREQ(offreId);

        OrderOffre savedOrder = OrderOffre.builder()
                .offre(offre)
                .demandeur(demandeur)
                .orderDate(new Date())
                .status(OrderStatus.NEW)
                .build();

        when(offreRepository.findById(offreId)).thenReturn(Optional.of(offre));
        when(userService.getAuthenticatedUser()).thenReturn(demandeur);
        when(orderRepository.save(any(OrderOffre.class))).thenReturn(savedOrder);
        when(orderMapper.toDTO(savedOrder)).thenReturn(new OffreOrderDTO());

        // Act
        OffreOrderDTO result = service.create(request);

        // Assert
        assertNotNull(result);
        verify(offreRepository).findById(offreId);
        verify(userService).getAuthenticatedUser();
        verify(orderRepository).save(any(OrderOffre.class));
        verify(orderMapper).toDTO(savedOrder);
    }

    @Test
    void CreateOrder_OffreNotFound() {
        // Arrange
        Long offreId = 1L;
        OffreOrderREQ request = new OffreOrderREQ(offreId);

        when(offreRepository.findById(offreId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OwnNotFoundException.class, () -> service.create(request));
        verify(offreRepository).findById(offreId);
        verifyNoInteractions(orderRepository, userService, orderMapper);
    }

    @Test
    void DeleteOrder_Success() {
        // Arrange
        Long orderId = 1L;
        OrderOffre order = new OrderOffre();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        service.delete(orderId);

        // Assert
        verify(orderRepository).findById(orderId);
        verify(orderRepository).delete(order);
    }

    @Test
    void DeleteOrder_NotFound() {
        // Arrange
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OwnNotFoundException.class, () -> service.delete(orderId));
        verify(orderRepository).findById(orderId);
        verify(orderRepository, never()).delete(any());
    }

    @Test
    void FindById_Success() {
        // Arrange
        Long orderId = 1L;
        OrderOffre order = new OrderOffre();
        OffreOrderDTO dto = new OffreOrderDTO();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.toDTO(order)).thenReturn(dto);

        // Act
        OffreOrderDTO result = service.findById(orderId);

        // Assert
        assertNotNull(result);
        verify(orderRepository).findById(orderId);
        verify(orderMapper).toDTO(order);
    }

    @Test
    void FindById_NotFound() {
        // Arrange
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OwnNotFoundException.class, () -> service.findById(orderId));
        verify(orderRepository).findById(orderId);
        verify(orderMapper, never()).toDTO(any());
    }

    // @Test
    // void testFetchOrdersByUser_Demandeur() {
    // // Arrange
    // Demandeur demandeur = new Demandeur();
    // demandeur.setOffreOrders(List.of(new OrderOffre()));
    // when(userService.getAuthenticatedUser()).thenReturn(demandeur);
    // when(orderMapper.toDTO(any())).thenReturn(new OffreOrderDTO());

    // // Act
    // List<OffreOrderDTO> result = service.fetchOrdersByUser();

    // // Assert
    // assertNotNull(result);
    // verify(userService).getAuthenticatedUser();
    // verify(orderMapper).toDTO(any());
    // }

    @Test
    void FindPage_Success() {
        // Arrange
        PageRequest pageable = PageRequest.of(0, 10);
        Page<OrderOffre> orders = new PageImpl<>(List.of(new OrderOffre()));
        when(orderRepository.findAll(pageable)).thenReturn(orders);
        when(orderMapper.toDTO(any())).thenReturn(new OffreOrderDTO());

        // Act
        Page<OffreOrderDTO> result = service.findPage(pageable);

        // Assert
        assertNotNull(result);
        verify(orderRepository).findAll(pageable);
        verify(orderMapper, times(1)).toDTO(any());
    }

    @Test
    void FindList() {
        // Arrange
        OrderOffre order1 = OrderOffre.builder().id(1L).status(OrderStatus.NEW).build();
        OrderOffre order2 = OrderOffre.builder().id(2L).status(OrderStatus.CONFIRMED).build();

        List<OrderOffre> orders = List.of(order1, order2);

        OffreOrderDTO dto1 = orderMapper.toDTO(order1);
        OffreOrderDTO dto2 = orderMapper.toDTO(order2);

        when(orderRepository.findAll()).thenReturn(orders);
        when(orderMapper.toDTO(order1)).thenReturn(dto1);
        when(orderMapper.toDTO(order2)).thenReturn(dto2);

        // Act
        List<OffreOrderDTO> result = service.findList();

        // Assert
        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));
        verify(orderRepository).findAll();
    }

    @Test
    void ConfirmOrder_Success() {
        // Arrange
        Long orderId = 1L;
        Prestataire prestataire = Prestataire.builder().id(1L).build();
        User authenticatedUser = prestataire;

        PrestataireServices prestataireService = PrestataireServices.builder()
                .prestataire(prestataire)
                .build();

        Offre offre = Offre.builder()
                .id(1L)
                .prestataireService(prestataireService)
                .build();

        OrderOffre order = OrderOffre.builder()
                .id(orderId)
                .offre(offre)
                .status(OrderStatus.NEW)
                .build();

        OrderOffre updatedOrder = OrderOffre.builder()
                .id(orderId)
                .offre(offre)
                .status(OrderStatus.CONFIRMED)
                .build();

        OffreOrderDTO expectedDTO = orderMapper.toDTO(updatedOrder);

        when(userService.getAuthenticatedUser()).thenReturn(authenticatedUser);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(updatedOrder);
        when(orderMapper.toDTO(updatedOrder)).thenReturn(expectedDTO);

        // Act
        OffreOrderDTO result = service.confirmOrder(orderId);

        // Assert
        assertEquals(expectedDTO, result);
        verify(userService).getAuthenticatedUser();
        verify(orderRepository).findById(orderId);
        verify(orderRepository).save(order);
    }

    @Test
    void CancelOrder_Success() {
        // Arrange
        Long orderId = 1L;
        Prestataire prestataire = Prestataire.builder().id(1L).build();
        User authenticatedUser = prestataire;

        PrestataireServices prestataireService = PrestataireServices.builder()
                .prestataire(prestataire)
                .build();

        Offre offre = Offre.builder()
                .id(1L)
                .prestataireService(prestataireService)
                .build();

        OrderOffre order = OrderOffre.builder()
                .id(orderId)
                .offre(offre)
                .status(OrderStatus.NEW)
                .build();

        OrderOffre updatedOrder = OrderOffre.builder()
                .id(orderId)
                .offre(offre)
                .status(OrderStatus.CANCELED)
                .build();

        OffreOrderDTO expectedDTO = orderMapper.toDTO(updatedOrder);

        when(userService.getAuthenticatedUser()).thenReturn(authenticatedUser);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(updatedOrder);
        when(orderMapper.toDTO(updatedOrder)).thenReturn(expectedDTO);

        // Act
        OffreOrderDTO result = service.cancelOrder(orderId);

        // Assert
        assertEquals(expectedDTO, result);
        verify(userService).getAuthenticatedUser();
        verify(orderRepository).findById(orderId);
        verify(orderRepository).save(order);
    }
    
}
