package com.kohub.coworking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Long id;

    @Column(nullable = false, unique = true)
    private String ref;

    @ManyToOne
    @JoinColumn(name = "id_espace", nullable = false)
    private Espace espace;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(name = "date_reservation", nullable = false)
    private LocalDate dateReservation;

    @Column(name = "heure_debut", nullable = false)
    private LocalTime heureDebut;

    private Integer duree;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReservationDetail> options = new HashSet<>();

    // Helper method pour gérer la relation bidirectionnelle
    public void addOption(ReservationDetail detail) {
        options.add(detail);
        detail.setReservation(this);
    }

    public void removeOption(ReservationDetail detail) {
        options.remove(detail);
        detail.setReservation(null);
    }
} 