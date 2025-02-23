package com.kohub.coworking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Paiement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paiement")
    private Long id;

    @Column(name = "ref_paiement", nullable = false, unique = true)
    private String refPaiement;

    @ManyToOne
    @JoinColumn(name = "ref_reservation", referencedColumnName = "ref", nullable = false)
    private Reservation reservation;

    @Column(name = "date_paiement", nullable = false)
    private LocalDate datePaiement;
} 