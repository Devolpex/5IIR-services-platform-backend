package org._iir.backend.modules.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import org._iir.backend.modules.demande.Demande;
import org._iir.backend.modules.service.Service;
@Table(name = "orders_table")

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Service service;

    @ManyToOne
    private Demande demande;

    private Date dateConfirmation;
    private String statut;

}
