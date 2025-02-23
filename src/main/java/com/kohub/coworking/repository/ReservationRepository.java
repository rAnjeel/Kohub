package com.kohub.coworking.repository;

import com.kohub.coworking.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByRef(String ref);
} 