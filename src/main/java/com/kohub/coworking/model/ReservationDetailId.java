package com.kohub.coworking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDetailId implements Serializable {
    private Long reservation;  // Doit correspondre au nom de l'attribut dans ReservationDetail
    private Long option;      // Doit correspondre au nom de l'attribut dans ReservationDetail
} 