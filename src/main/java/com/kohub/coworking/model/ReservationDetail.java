package com.kohub.coworking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation_detail")
@IdClass(ReservationDetailId.class)
public class ReservationDetail {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_reservation", nullable = false)
    @JsonIgnore
    private Reservation reservation;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_option", nullable = false)
    private Option option;
} 