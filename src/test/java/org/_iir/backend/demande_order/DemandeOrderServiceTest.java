package org._iir.backend.demande_order;

import org._iir.backend.exception.OwnNotFoundException;
import org._iir.backend.modules.demande.Demande;
import org._iir.backend.modules.demandeur.Demandeur;
import org._iir.backend.modules.order.OrderStatus;
import org._iir.backend.modules.order.demande.DemandeOrder;
import org._iir.backend.modules.order.demande.DemandeOrderMapperImpl;
import org._iir.backend.modules.order.demande.DemandeOrderREQ;
import org._iir.backend.modules.order.demande.DemandeOrderRepository;
import org._iir.backend.modules.order.demande.DemandeOrderServiceImpl;
import org._iir.backend.modules.order.dto.DemandeOrderDTO;
import org._iir.backend.modules.prestataire.Prestataire;
import org._iir.backend.modules.proposition.Proposition;
import org._iir.backend.modules.proposition.PropositionDao;
import org._iir.backend.modules.user.Role;
import org._iir.backend.modules.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DemandeOrderServiceTest {

    @InjectMocks
    private DemandeOrderServiceImpl service;

    @Mock
    private PropositionDao propositionDao;

    @Mock
    private UserService userService;

    @Mock
    private DemandeOrderRepository orderRepository;

    @Mock
    private DemandeOrderMapperImpl orderMapper;

    private DemandeOrderREQ request;
    private Proposition proposition;
    private Demandeur demandeur;
    private DemandeOrder order;
    private DemandeOrderDTO orderDTO;

    private List<DemandeOrder> orders;
    private Pageable pageable;
    private Page<DemandeOrder> orderPage;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 10);
        request = new DemandeOrderREQ(1L);

        demandeur = new Demandeur();
        demandeur.setId(1L);
        demandeur.setRole(Role.DEMANDEUR);

        Prestataire prestataire = new Prestataire();
        prestataire.setId(1L);
        prestataire.setRole(Role.PRESTATAIRE);

        Demande mockDemande = new Demande();
        mockDemande.setDemandeur(demandeur);

        proposition = new Proposition();
        proposition.setId(1L);
        proposition.setDemande(mockDemande);
        proposition.setPrestataire(prestataire);

        order = DemandeOrder.builder()
                .id(1L)
                .proposition(proposition)
                .status(OrderStatus.NEW)
                .orderDate(new Date())
                .build();

        orderDTO = new DemandeOrderDTO();

        DemandeOrder order1 = DemandeOrder.builder()
                .id(1L)
                .status(OrderStatus.NEW)
                .orderDate(new Date())
                .build();
        DemandeOrder order2 = DemandeOrder.builder()
                .id(2L)
                .status(OrderStatus.CONFIRMED)
                .orderDate(new Date())
                .build();
        orders = List.of(order1, order2);

        orderPage = new PageImpl<>(orders, pageable, orders.size());
    }

    @Test
    void create_Success() {
        // Arrange
        when(propositionDao.findById(request.propositionId())).thenReturn(Optional.of(proposition));
        when(userService.getAuthenticatedUser()).thenReturn(demandeur);
        when(orderRepository.save(any(DemandeOrder.class))).thenReturn(order);
        when(orderMapper.toDTO(any(DemandeOrder.class))).thenReturn(orderDTO);

        // Act
        DemandeOrderDTO result = service.create(request);

        // Assert
        assertNotNull(result);
        verify(propositionDao).findById(request.propositionId());
        verify(userService).getAuthenticatedUser();
        verify(orderRepository).save(any(DemandeOrder.class));
        verify(orderMapper).toDTO(any(DemandeOrder.class));
    }

    @Test
    void create_PropositionNotFound() {
        when(propositionDao.findById(request.propositionId())).thenReturn(Optional.empty());

        OwnNotFoundException exception = assertThrows(OwnNotFoundException.class, () -> service.create(request));
        assertEquals("Proposition not exists", exception.getMessage());

        verify(propositionDao).findById(request.propositionId());
        verifyNoInteractions(userService, orderRepository, orderMapper);
    }

    @Test
    void create_DemandeurNotOwner() {
        Demandeur otherDemandeur = new Demandeur();
        otherDemandeur.setId(2L);

        when(propositionDao.findById(request.propositionId())).thenReturn(Optional.of(proposition));
        when(userService.getAuthenticatedUser()).thenReturn(otherDemandeur);

        OwnNotFoundException exception = assertThrows(OwnNotFoundException.class, () -> service.create(request));
        assertEquals("Demandeur is not the owner of demande", exception.getMessage());

        verify(propositionDao).findById(request.propositionId());
        verify(userService).getAuthenticatedUser();
        verifyNoInteractions(orderRepository, orderMapper);
    }

    @Test
    void delete_Success() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        service.delete(orderId);

        verify(orderRepository).findById(orderId);
        verify(orderRepository).delete(order);
    }

    @Test
    void delete_OrderNotFound() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        OwnNotFoundException exception = assertThrows(OwnNotFoundException.class, () -> service.delete(orderId));
        assertEquals("Order not exists", exception.getMessage());

        verify(orderRepository).findById(orderId);
        verify(orderRepository, never()).delete(any(DemandeOrder.class));
    }

    @Test
    void findById_Success() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.toDTO(order)).thenReturn(orderDTO);

        DemandeOrderDTO result = service.findById(orderId);

        assertNotNull(result);
        assertEquals(orderDTO, result);
        verify(orderRepository).findById(orderId);
        verify(orderMapper).toDTO(order);
    }

    @Test
    void findById_OrderNotFound() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        OwnNotFoundException exception = assertThrows(OwnNotFoundException.class, () -> service.findById(orderId));
        assertEquals("Order not exists", exception.getMessage());

        verify(orderRepository).findById(orderId);
        verify(orderMapper, never()).toDTO(any(DemandeOrder.class));
    }

    @Test
    void findList_Success() {
        when(orderRepository.findAll()).thenReturn(orders);
        when(orderMapper.toDTO(orders.get(0))).thenReturn(orderDTO);
        when(orderMapper.toDTO(orders.get(1))).thenReturn(orderDTO);

        List<DemandeOrderDTO> result = service.findList();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(orderRepository).findAll();
        verify(orderMapper).toDTO(orders.get(0));
        verify(orderMapper).toDTO(orders.get(1));
    }

    @Test
    void findList_EmptyList() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        List<DemandeOrderDTO> result = service.findList();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(orderRepository).findAll();
        verify(orderMapper, never()).toDTO(any(DemandeOrder.class));
    }

    @Test
    void findPage_Success() {
        when(orderRepository.findAll(pageable)).thenReturn(orderPage);
        when(orderMapper.toDTO(any(DemandeOrder.class)))
                .thenAnswer(invocation -> {
                    DemandeOrder order = invocation.getArgument(0);
                    DemandeOrderDTO dto = new DemandeOrderDTO();
                    dto.setId(order.getId());
                    dto.setStatus(order.getStatus());
                    return dto;
                });

        Page<DemandeOrderDTO> result = service.findPage(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(orderRepository).findAll(pageable);
        verify(orderMapper, times(2)).toDTO(any(DemandeOrder.class));
    }

    @Test
    void findPage_EmptyPage() {
        when(orderRepository.findAll(pageable)).thenReturn(Page.empty());

        Page<DemandeOrderDTO> result = service.findPage(pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(orderRepository).findAll(pageable);
        verify(orderMapper, never()).toDTO(any(DemandeOrder.class));
    }
}
